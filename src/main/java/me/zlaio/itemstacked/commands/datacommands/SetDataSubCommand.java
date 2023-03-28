package me.zlaio.itemstacked.commands.datacommands;

import me.zlaio.itemstacked.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SetDataSubCommand extends SubCommand {

    private final JavaPlugin plugin;

    public SetDataSubCommand(JavaPlugin plugin) {
        this.plugin = plugin;

        loadSubCommands();
    }

    private void loadSubCommands() {
        addSubCommand("pdc", new SetPersistentDataSubCommand(plugin));
        addSubCommand("custom_model_data", new SetCustomModelDataSubCommand());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length < 3) {
            sendAllSubCommandSnippets(player);
            return;
        }

        String subCommand = args[2];

        if (!isSubCommand(subCommand)) {
            sendMessage(player, "&cUnknown command run /is data set to view all available commands");
            return;
        }

        runSubCommand(subCommand, sender, args);
    }

    @Override
    public String getDescription() {
        return "Set data on an item";
    }

    @Override
    public String getUsage() {
        return "/is data set <type>";
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {

        if (args.length == 3)
            return new ArrayList<>(getSubCommandNames());

        String subCommand = args[2];

        if (!isSubCommand(subCommand))
            return new ArrayList<>();

        return getTabCompletions(subCommand, sender, args);
    }

}
