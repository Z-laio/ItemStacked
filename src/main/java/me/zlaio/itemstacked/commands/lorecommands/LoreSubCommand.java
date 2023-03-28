package me.zlaio.itemstacked.commands.lorecommands;

import me.zlaio.itemstacked.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LoreSubCommand extends SubCommand {

    {
        addSubCommand("set", new SetLineSubCommand());
        addSubCommand("insert", new InsertLineSubCommand());
        addSubCommand("remove", new RemoveLineSubCommand());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length < 2) {
            sendAllSubCommandSnippets(player);
            return;
        }

        String subCommand = args[1];

        if (!isSubCommand(subCommand)) {
            sendMessage(player, "&cUnknown command run /is lore to view all available commands");
            return;
        }

        runSubCommand(subCommand, sender, args);
    }


    @Override
    public String getDescription() {
        return "Alters the lines of lore set on an item";
    }

    @Override
    public String getUsage() {
        return "/is lore &7[&eset&7|&einsert&7|&eremove&7]";
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
