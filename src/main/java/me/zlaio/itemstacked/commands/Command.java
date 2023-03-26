package me.zlaio.itemstacked.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public abstract class Command implements CommandExecutor {

    abstract public void execute(CommandSender sender, String[] args);

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        execute(sender, args);
        return false;
    }

    public String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', "&r" + s);
    }
    
}
