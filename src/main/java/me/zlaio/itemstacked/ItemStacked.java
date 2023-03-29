package me.zlaio.itemstacked;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public final class ItemStacked extends JavaPlugin {

    private static final String DEFAULT_SAVE_FILE_NAME = "items";
    private static final File DEFAULT_FILE = new File("plugins/ItemStacked/" + DEFAULT_SAVE_FILE_NAME + ".yml");
    private static ItemStacked instance;

    private static void reloadCommands(ItemProvider itemProvider, JavaPlugin plugin, YAMLFile saveFile) {
        ItemStackedCommand itemStackedCommand = new ItemStackedCommand(itemProvider, plugin, saveFile, true);
        instance.getCommand("itemstacked").setExecutor(itemStackedCommand);
        instance.getCommand("itemstacked").setTabCompleter(itemStackedCommand);
    }

    /***
     * Use this version of the method to set a
     * specific file location
     * @param file The file path that the plugin will use to
     *             save/load items to and from
     * @return ItemProvider an instance to create custom functionality
     */
    public static ItemProvider getItemProvider(File file) {
        return getItemProvider(file, instance);
    }

    /***
     * Use this version of the method to set both a
     * specific file location and plugin namespace
     * for persistent data storage on items
     * @param file The file path that the plugin will use to
     *             save/load items to and from
     * @param plugin An instance of a plugin to use that plugin's namespace
     * @return ItemProvider an instance to create custom functionality
     */
    public static ItemProvider getItemProvider(File file, JavaPlugin plugin) {
        YAMLFile itemFile = new YAMLFile(file, instance.getResource("items.yml"));
        ItemProvider itemProvider = new ItemProvider(itemFile, plugin);

        reloadCommands(itemProvider, plugin, itemFile);

        return itemProvider;
    }

    /***
     * Use this version of the method to set use
     * a different plugin namespace for persistent
     * data storage on items
     * @param plugin An instance of a plugin to use that plugin's namespace
     * @return ItemProvider an instance to create custom functionality
     */
    public static ItemProvider getItemProvider(JavaPlugin plugin) {
        return getItemProvider(DEFAULT_FILE, plugin);
    }

    protected static ItemStacked getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveResource("config.yml", false);

        reload();
    }

    public void reload() {
        reloadConfig();

        boolean shouldUseSaveFileName = getConfig().getBoolean("use-save-file-name");
        String saveFileName = getSaveFileName(shouldUseSaveFileName);

        //Only register /is huh? command
        if (!shouldUseSaveFileName) {
            ItemStackedCommand itemStackedCommand = new ItemStackedCommand(null, null, null, shouldUseSaveFileName);
            instance.getCommand("itemstacked").setExecutor(itemStackedCommand);
            instance.getCommand("itemstacked").setTabCompleter(itemStackedCommand);

            return;
        }


        File saveFile = new File(String.format("%s/%s.yml", getDataFolder(), saveFileName));

        getItemProvider(saveFile, this);
    }

    private String getSaveFileName(boolean shouldUseSaveFileName) {
        String saveFileNameField = getConfig().getString("save-file-name");
        boolean hasSaveFileName;

        if (saveFileNameField == null)
            hasSaveFileName = false;
        else
            hasSaveFileName = !saveFileNameField.isEmpty();

        if (!hasSaveFileName && !shouldUseSaveFileName) {
            getLogger().log(Level.CONFIG, "Invalid or missing 'save-file-name' field, using default file name "
                    + DEFAULT_SAVE_FILE_NAME + "'");

            return DEFAULT_SAVE_FILE_NAME;
        }

        return saveFileNameField;
    }

}
