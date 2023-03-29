package me.zlaio.itemstacked;

import java.util.*;

import me.zlaio.itemstacked.exceptions.InvalidItemConfigurationException;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;

public class ItemProvider {
    
    private final YAMLFile itemFile;
    private final JavaPlugin plugin;

    public ItemProvider(YAMLFile itemFile, JavaPlugin plugin) {
        this.plugin = plugin;
        this.itemFile = itemFile;
    }
 
    /***
     * 
     * @param itemName The name used to save the item under in the file
     * @param item The ItemStack instance that is saved
     */

    public void saveItem(String itemName, ItemStack item) {
        FileConfiguration config = itemFile.getConfig();

        String itemPath = getItemPath(itemName);

        Material material = item.getType();
        String displayName = item.getItemMeta().getDisplayName();
        List<String> lore = item.getItemMeta().getLore();

        config.set(itemPath + "material", material.toString());
        config.set(itemPath + "display_name", displayName);
        config.set(itemPath + "lore", lore);

        saveItemData(itemName, item);

        itemFile.saveConfig();
    }

    public void deleteItem(String itemName) {
        itemFile.getConfig().set("items." + itemName, null);
        itemFile.saveConfig();
    }

    /***
     * Will return the itemstack from the file where it was saved if it has been saved.
     * Otherwise, will return null
     * <br><br>
     * @param itemName What to save the ItemStack instance as
     * @return The ItemStack that was saved to the file under the itemName
     * @throws InvalidItemConfigurationException if the item has a missing or invalid "material" key and or value
     */

    @Nullable
    public ItemStack getItem(String itemName) {

        if (!getAllSavedItemNames().contains(itemName))
            return null;

        FileConfiguration config = itemFile.getConfig();
        String itemPath = getItemPath(itemName);

        Material material = getMaterial(itemName);
        String displayName = getDisplayName(itemPath, material);
        List<String> lore = getLore(itemName);

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (config.getString(itemPath + "custom_model_data") != null) {
            int customModelData = config.getInt(itemPath + "custom_model_data");
            meta.setCustomModelData(customModelData);
        }

        setItemData(itemName, meta);

        meta.setDisplayName(displayName);
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    public List<String> getAllSavedItemNames() {
        return new ArrayList<>(itemFile.getConfig().getConfigurationSection("items").getKeys(false));
    }

    /***
     * This version of the method will use the ItemStacked plugin's
     * namespace for persistent data containers
     * @return A list of all ItemStack instances, deserialized from the file
     */
    public List<ItemStack> getAllItems() {
        Set<String> itemNames = itemFile.getConfig()
                                .getConfigurationSection("items")
                                .getKeys(false);

        List<ItemStack> savedItems = new ArrayList<>();

        for (String itemName : itemNames) {
            ItemStack itemFromFile = getItem(itemName);
            savedItems.add(itemFromFile);
        }

        return savedItems;
    }

    private void saveItemData(String itemName, ItemStack item) {
        FileConfiguration config = itemFile.getConfig();

        String itemPath = getItemPath(itemName) + "data.";

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        for (NamespacedKey namespacedKey : container.getKeys()) {
            String key = namespacedKey.getKey();
            String value = container.get(namespacedKey, PersistentDataType.STRING);

            config.set(itemPath + key, value);
        }
    }

    private void setItemData(String itemName, ItemMeta meta) {
        String itemPath = getItemPath(itemName);
        Map<String, String> dataEntries = getDataEntries(itemName);

        for (Map.Entry<String, String> dataEntry : dataEntries.entrySet()) {
            String key = dataEntry.getKey();
            String value = dataEntry.getValue();
            setItemData(meta, key, value);
        }
    }

    private void setItemData(ItemMeta meta, String key, String value) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
    }

    private String getItemPath(String itemName) {
        return "items." + itemName + ".";
    }

    private Material getMaterial(String itemName) {
        FileConfiguration config = itemFile.getConfig();
        String itemPath = getItemPath(itemName);

        if (config.getString(itemPath + "material") == null)
            throw new InvalidItemConfigurationException(itemName);

        String configMaterial = config.getString(itemPath + "material");
        Material material = Material.matchMaterial(configMaterial);

        if (material == null)
            throw new InvalidItemConfigurationException(itemName);

        return material;
    }

    private String getDisplayName(String itemPath, Material materialToUseIfNoDisplayNameIsFound) {
        FileConfiguration config = itemFile.getConfig();

        String displayName = config.getString(itemPath + "display_name");

        if (displayName == null)
            displayName = new ItemStack(materialToUseIfNoDisplayNameIsFound).getItemMeta().getDisplayName();

        displayName = ChatColor.translateAlternateColorCodes('&', displayName);

        return displayName;
    }

    private List<String> getLore(String itemName) {
        FileConfiguration config = itemFile.getConfig();
        String itemPath = getItemPath(itemName);

        List<String> lore = config.getStringList(itemPath + "lore");
        lore = formatLore(lore);

        return lore;
    }

    private List<String> formatLore(List<String> lore) {
        List<String> formattedLore = new ArrayList<>();

        for (String line : lore)
            formattedLore.add(ChatColor.translateAlternateColorCodes('&', line));

        return formattedLore;
    }

    private Map<String, String> getDataEntries(String itemName) {
        FileConfiguration config = itemFile.getConfig();
        String itemPath = getItemPath(itemName);

        Map<String, String> dataEntries = new HashMap<>();

        for (String key : config.getConfigurationSection(itemPath + "data").getKeys(false)) {
            String value = config.getString(itemPath + "data." + key);
            dataEntries.put(key, value);
        }

        return dataEntries;
    }


}