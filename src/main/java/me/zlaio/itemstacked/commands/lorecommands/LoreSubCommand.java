package me.zlaio.itemstacked.commands.lorecommands;

import me.zlaio.itemstacked.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoreSubCommand extends SubCommand {

    private final HashMap<String, SubCommand> subCommands = new HashMap<>();

    {
        subCommands.put("set", new SetLineSubCommand());
        subCommands.put("insert", new InsertLineSubCommand());
        subCommands.put("remove", new RemoveLineSubCommand());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length < 2) {
            sendAllSubCommandSnippets(player);
            return;
        }

        String subCommand = args[1];

        if (!hasSubCommand(subCommand)) {
            sendMessage(player, "&cUnknown command run /is lore to view all available commands");
            return;
        }

        runSubCommand(subCommand, sender, args);
    }

    private boolean hasSubCommand(String subCommand) {
        return subCommands.containsKey(subCommand);
    }

    private void runSubCommand(String subCommand, CommandSender sender, String[] args) {
        subCommands.get(subCommand).execute(sender, args);
    }

    private void sendAllSubCommandSnippets(Player player) {
        for (SubCommand subCommand : subCommands.values()) {
            player.sendMessage(format("&e" + subCommand.getUsage() + " &f- &7" + subCommand.getDescription()));
        }
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
            return new ArrayList<>(subCommands.keySet());

        String subCommand = args[1];

        if (!hasSubCommand(subCommand))
            return new ArrayList<>();

        return subCommands.get(subCommand).getTabCompletions(sender, args);
    }
}
