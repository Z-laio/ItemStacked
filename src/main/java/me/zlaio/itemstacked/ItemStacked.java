package me.zlaio.itemstacked;

import org.bukkit.plugin.java.JavaPlugin;

import me.zlaio.itemstacked.commands.ItemStackedCommand;

public final class ItemStacked extends JavaPlugin {
    
    private ItemProvider itemProvider;

    @Override
    public void onEnable() {
        YAMLFile itemFile = new YAMLFile("items", "items.yml", this);

        itemProvider = new ItemProvider(itemFile);

        ItemStackedCommand itemStackedCommand = new ItemStackedCommand(itemProvider, this, itemFile);
        getCommand("itemstacked").setExecutor(itemStackedCommand);
        getCommand("itemstacked").setTabCompleter(itemStackedCommand);
    }
    
}
