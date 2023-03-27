package me.zlaio.itemstacked.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public abstract class Command implements CommandExecutor {

    public final String COMMAND_PREFIX = "&7[&eItemStacked&7]&e: ";

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
