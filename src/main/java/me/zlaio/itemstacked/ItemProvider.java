package me.zlaio.itemstacked;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.inventory.ItemStack;

import me.zlaio.itemstacked.exceptions.ItemNotFoundException;

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
    }

    /***
     * 
     * @param itemName
     * @return The ItemStack that was saved to the file under the itemName
     * @throws {@link me.zlaio.itemstacked.exceptions.ItemNotFoundException ItemNotFoundException}
     */
    public ItemStack getItem(String itemName) {
        ItemStack item = itemFile.getConfig().getItemStack("items." + itemName);

        if (item == null) {
            throw new ItemNotFoundException(itemName, itemFile.getName());
        }

        return item;
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
