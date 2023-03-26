package me.zlaio.itemstacked;

import org.bukkit.plugin.java.JavaPlugin;

public class ItemStacked extends JavaPlugin {

    private ItemProvider itemProvider;

    @Override
    public void onEnable() {
        YAMLFile itemFile = new YAMLFile("items", this);

        itemProvider = new ItemProvider(itemFile);
    }
    
}
