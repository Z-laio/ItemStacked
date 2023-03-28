package me.zlaio.itemstacked;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.zlaio.itemstacked.exceptions.InvalidItemConfigurationException;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;

public class ItemProvider {
    
    private final YAMLFile itemFile;

    public ItemProvider(YAMLFile itemFile) {
        this.itemFile = itemFile;
    }
 
    /***
     * 
     * @param itemName The name used to save the item under in the file
     * @param item The ItemStack instance that is saved
     */

    public void saveItem(String itemName, ItemStack item) {
        FileConfiguration config = itemFile.getConfig();

        String itemPath = "items." + itemName + ".";

        Material material = item.getType();
        String displayName = item.getItemMeta().getDisplayName();
        List<String> lore = item.getItemMeta().getLore();

        config.set(itemPath + "material", material.toString());
        config.set(itemPath + "display_name", displayName);
        config.set(itemPath + "lore", lore);

        itemFile.save();
    }

    public void deleteItem(String itemName) {
        itemFile.getConfig().set("items." + itemName, null);
        itemFile.save();
    }

    /***
     * Will return the itemstack from the file where it was saved if it has been saved.
     * Otherwise, will return null
     * @param itemName What to save the ItemStack instance as
     * @return The ItemStack that was saved to the file under the itemName
     */

    @Nullable
    public ItemStack getItem(String itemName) {

        if (!getAllSavedItemNames().contains(itemName))
            return null;

        FileConfiguration config = itemFile.getConfig();
        String itemPath = "items." + itemName + ".";

        if (config.getString(itemPath + "material") == null)
            throw new InvalidItemConfigurationException();

        String configMaterial = config.getString(itemPath + "material");
        Material material = Material.matchMaterial(configMaterial);

        if (material == null)
            throw new InvalidItemConfigurationException();

        String displayName = config.getString(itemPath + "display_name");

        if (displayName == null)
            displayName = new ItemStack(material).getItemMeta().getDisplayName();

        List<String> lore = config.getStringList(itemPath + "lore");
        lore = formatLore(lore);

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayName);
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    private List<String> formatLore(List<String> lore) {
        List<String> formattedLore = new ArrayList<>();

        for (String line : lore)
            formattedLore.add(ChatColor.translateAlternateColorCodes('&', line));

        return formattedLore;
    }

    public List<String> getAllSavedItemNames() {
        return new ArrayList<>(itemFile.getConfig().getConfigurationSection("items").getKeys(false));
    }

    /***
     * 
     * @return A list of all ItemStack instances, deserialized from the file
     */
    public List<ItemStack> getAllItems() {
        Set<String> itemNames = itemFile.getConfig()
                                .getConfigurationSection("items")
                                .getKeys(false);

        List<ItemStack> savedItems = new ArrayList<ItemStack>();       
        
        for (String itemName : itemNames) {
            ItemStack itemFromFile = getItem(itemName);
            savedItems.add(itemFromFile);
        }

        return savedItems;
    }
    
}
