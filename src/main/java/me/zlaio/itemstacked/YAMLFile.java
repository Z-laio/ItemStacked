package me.zlaio.itemstacked;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YAMLFile {

    private static final ItemStacked plugin = ItemStacked.getInstance();

    private final File configFile;
    private FileConfiguration config;

    public YAMLFile(File file, InputStream resource) {
        this.configFile = file;

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();

            try {
                configFile.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
                plugin.getLogger().log(Level.SEVERE, String.format("Couldn't create file '%s'", getName()));
                return;
            }

            config = YamlConfiguration.loadConfiguration(new InputStreamReader(resource));
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
