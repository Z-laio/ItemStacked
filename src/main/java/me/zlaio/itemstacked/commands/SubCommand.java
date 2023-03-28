package me.zlaio.itemstacked.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand extends Command {

    abstract public String getDescription();

    abstract public String getUsage();

    abstract public List<String> getTabCompletions(CommandSender sender, String[] args);

}
