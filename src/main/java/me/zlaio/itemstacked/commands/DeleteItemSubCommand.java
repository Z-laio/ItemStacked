package me.zlaio.itemstacked.commands;

import me.zlaio.itemstacked.ItemProvider;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DeleteItemSubCommand extends SubCommand {

    private final ItemProvider itemProvider;

    public DeleteItemSubCommand(ItemProvider itemProvider) {
        this.itemProvider = itemProvider;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (! (sender instanceof Player))
            return;

        Player player = (Player) sender;

        if (args.length < 2) {
            sendMessage(player, "&cUsage: &c" + getUsage());
            return;
        }

        String itemName = args[1];
        itemProvider.deleteItem(itemName);
        sendMessage(player, "&eItem &7'&e" + itemName + "&7'&e has been deleted");
    }

    @Override
    public String getDescription() {
        return "Deletes an item from the save file";
    }

    @Override
    public String getUsage() {
        return "/is delete <itemName>";
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {

        if (args.length == 2)
            return itemProvider.getAllSavedItemNames();

        return new ArrayList<>();
    }

}
