package me.zlaio.itemstacked;

import org.bukkit.plugin.java.JavaPlugin;

import me.zlaio.itemstacked.commands.ItemStackedCommand;

public class ItemStacked extends JavaPlugin {
    
    private ItemProvider itemProvider;

    @Override
    public void onEnable() {
        YAMLFile itemFile = new YAMLFile("items", this);

        itemProvider = new ItemProvider(itemFile);
    
        getCommand("itemstacked").setExecutor(new ItemStackedCommand(itemProvider, itemFile));
    }
    
}
