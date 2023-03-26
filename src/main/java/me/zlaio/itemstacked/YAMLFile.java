package me.zlaio.itemstacked;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class YAMLFile {
    
    private File configFile;
    private FileConfiguration config;
    private final JavaPlugin plugin;

    public YAMLFile(String fileName, JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfiguration(fileName);
        save();
    }

    public String getName() {
        return configFile.getName();
    }

    public FileConfiguration getConfig() {
        return config;
    }
    
    private void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Couldn't save file (" + configFile.getName() + ")");
        }
    }

    public void reload() {
        save();
        loadConfiguration(configFile.getName());
    }

    private void loadConfiguration(String fileName) {
        configFile = new File(plugin.getDataFolder() + fileName + ".yml");

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();

            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            config = new YamlConfiguration();

        } else config = YamlConfiguration.loadConfiguration(configFile);
        
    }


}