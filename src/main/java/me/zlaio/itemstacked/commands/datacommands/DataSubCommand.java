package me.zlaio.itemstacked.commands.datacommands;

import me.zlaio.itemstacked.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class DataSubCommand extends SubCommand {

    private final JavaPlugin plugin;

    public DataSubCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        loadSubCommands();
    }

    private void loadSubCommands() {
        addSubCommand("set", new SetDataSubCommand(plugin));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length < 1) {
            sendAllSubCommandSnippets(player);
            return;
        }

        String subCommand = args[1];

        if (!isSubCommand(subCommand)) {
            sendMessage(player, "&cUnknown command run /is data to view all available commands");
            return;
        }

        runSubCommand(subCommand, sender, args);
    }

    @Override
    public String getDescription() {
        return "Alter an items stored data";
    }

    @Override
    public String getUsage() {
        return "/is data &7[&eset&7]";
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {

        if (args.length == 2)
            return new ArrayList<>(getSubCommandNames());

        String subCommand = args[1];

        if (!isSubCommand(subCommand))
            return new ArrayList<>();

        return getTabCompletions(subCommand, sender, args);
    }

}
