package me.zlaio.itemstacked.commands.lorecommands;

import me.zlaio.itemstacked.commands.CommandUtils;
import me.zlaio.itemstacked.commands.SubCommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class RemoveLineSubCommand extends SubCommand {

    // /is lore remove int [int]

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length < 3) {
            sendMessage(player, "&cUsage: &e" + getUsage());
            return;
        }

        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem.getType() == Material.AIR) {
            sendMessage(player, "&cHold the item that you want to set lore on");
            return;
        }

        List<String> lore = CommandUtils.getLore(heldItem);

        //Range is being used
        if (args.length > 3) {

            String arg4 = args[3];
            String arg3 = args[2];

            int lowerBound;
            int upperBound;

            try {
                lowerBound = Integer.parseInt(arg3);
                upperBound = Integer.parseInt(arg4);
            } catch (NumberFormatException e) {
                sendMessage(player, "&cRanges should be integers");
                return;
            }

            if (lowerBound <= 0 || upperBound <= 0) {
                sendMessage(player, "&cRanges should be integers greater than 0");
                return;
            }

            if (lowerBound >= upperBound) {
                sendMessage(player, "&cRange bounds should be given in increasing order Ex. /is lore remove 1 4");
                return;
            }

            int loreLines = lore.size();

            if (upperBound > loreLines) {
                sendMessage(player, "&cUpper bound integer is greater than lore line count");
                return;
            }

            removeLoreRange(heldItem, lowerBound, upperBound);

        } else {


            String lineNumberAsString = args[2];
            int lineNumberAsInteger;

            try {
                lineNumberAsInteger = Integer.parseInt(lineNumberAsString);
            } catch (IllegalArgumentException e) {
                sendMessage(player, "&cLine numbers should be integers");
                return;
            }

            if (lineNumberAsInteger < 1) {
                sendMessage(player, "&cThe line number should be an integer greater than 0");
                return;
            }

            if (lineNumberAsInteger > lore.size()) {
                sendMessage(player, "&cLine number " + lineNumberAsInteger + " doesn't exist on this item");
                return;
            }

            removeLore(heldItem, lineNumberAsInteger);
            sendMessage(player, "&eLore updated");

        }

    }

    private void removeLoreRange(ItemStack item, int lowerBound, int upperBound) {
        for (int i = 0; i < upperBound - lowerBound; i++) {
            removeLore(item, lowerBound);
        }
    }

    private void removeLore(ItemStack item, int lineNumber) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.remove(lineNumber - 1);
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    @Override
    public String getDescription() {
        return "Removes lore lines from an item";
    }

    @Override
    public String getUsage() {
        return "/is lore remove <lineNumber/lowerBound> [upperBound]";
    }
}
