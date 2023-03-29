package me.zlaio.itemstacked.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Command implements CommandExecutor {

    public static final String COMMAND_PREFIX = "&7[&eItemStacked&7]&e: ";

    private Map<String, SubCommand> subCommands = new HashMap<>();

    public void addSubCommand(String subCommand, SubCommand executor) {
        subCommands.put(subCommand, executor);
    }

    public void runSubCommand(String subCommand, CommandSender sender, String[] args) {
        subCommands.get(subCommand).execute(sender, args);
    }

    public boolean isSubCommand(String subCommand) {
        return subCommands.containsKey(subCommand);
    }

    public Set<Map.Entry<String, SubCommand>> getSubCommandEntrySet() {
        return subCommands.entrySet();
    }

    public Set<String> getSubCommandNames() {
        return subCommands.keySet();
    }

    public List<String> getTabCompletions(String subCommand, CommandSender sender, String[] args) {
        return subCommands.get(subCommand).getTabCompletions(sender, args);
    }

    public void sendAllSubCommandSnippets(Player player) {
        String prefix = COMMAND_PREFIX.replace(":", "");

        player.sendMessage(format("&e-----" + prefix + "-----"));
        player.sendMessage(format("&7[] &f- &eoptional parameter"));
        player.sendMessage(format("&7<> &f- &erequired parameter"));

        for (Map.Entry<String, SubCommand> entry : getSubCommandEntrySet()) {
            SubCommand subCommand = entry.getValue();
            player.sendMessage(format("&e" + subCommand.getUsage() + " &f- &7" + subCommand.getDescription()));
        }
    }

    abstract public void execute(CommandSender sender, String[] args);

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        execute(sender, args);
        return false;
    }

    public String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', "&r" + s);
    }
    
    public void sendMessage(Player player, String message) {
     player.sendMessage(format(COMMAND_PREFIX + message));
    }
    
}
