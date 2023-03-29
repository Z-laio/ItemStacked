package me.zlaio.itemstacked.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WhereAreMyCommandsSubCommand extends SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        String message = "&eYou've set &7'use-save-file-name' &eto &7false &eor " +
                "have not set it at all. To enable the commands you must first hook into the API and call " +
                "one of the provided &7'ItemStacked.getItemProvider()' &emethods, if this was a mistake just set " +
                "&7'use-save-file-name' &e to &etrue and do &7'/is reload'";

        sendMessage(player, message);
    }

    @Override
    public String getDescription() {
        return "Find out where the commands went";
    }

    @Override
    public String getUsage() {
        return "/is huh?";
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
