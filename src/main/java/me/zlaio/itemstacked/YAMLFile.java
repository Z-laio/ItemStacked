package me.zlaio.itemstacked;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YAMLFile {

    private final File configFile;
    private final ItemStacked plugin;

    private FileConfiguration config;

    public YAMLFile(String filePath, String resource, ItemStacked plugin) {
        this.plugin = plugin;

        configFile = new File(plugin.getDataFolder() + "/" + filePath + ".yml");

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();

            try {
                configFile.createNewFile();
            } catch (IOException ignored) {}

            InputStream inputStream = plugin.getResource(resource);

            config = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
            saveConfig();
        } else {
            config = YamlConfiguration.loadConfiguration(configFile);
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public String getName() {
        return configFile.getName();
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Couldn't save file (" + configFile.getName() + ")");
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

}
