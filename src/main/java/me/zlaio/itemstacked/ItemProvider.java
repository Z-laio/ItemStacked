package me.zlaio.itemstacked;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.inventory.ItemStack;

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
        itemFile.getConfig().set("items." + itemName, item);
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
        return itemFile.getConfig().getItemStack("items." + itemName);
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
