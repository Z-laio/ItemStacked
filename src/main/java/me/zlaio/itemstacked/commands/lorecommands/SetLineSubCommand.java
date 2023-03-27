package me.zlaio.itemstacked.commands.lorecommands;

import me.zlaio.itemstacked.commands.CommandUtils;
import me.zlaio.itemstacked.commands.SubCommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SetLineSubCommand extends SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length < 4) {
            sendMessage(player, "&cUsage: &e" + getUsage());
            return;
        }

        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem.getType() == Material.AIR) {
            sendMessage(player, "&cHold the item that you want to set lore on");
            return;
        }

        String lineNumberAsString = args[2];
        int lineNumberAsInteger;

        try {
            lineNumberAsInteger = Integer.parseInt(lineNumberAsString);
        } catch (IllegalArgumentException e) {
            sendMessage(player, "&cEnter an integer for the line number");
            return;
        }

        if (lineNumberAsInteger < 1) {
            sendMessage(player, "&cEnter an integer greater than 0");
            return;
        }

        String loreLine = CommandUtils.getStringFromArgs(3, args);

        if (!CommandUtils.argumentHasValidQuotations(loreLine)) {
            sendMessage(player, "&cWrap the new line in quotes | \"line\"");
            return;
        }

        loreLine = CommandUtils.getContentBetweenQuotes(loreLine);

        //Make text default to white
        loreLine = "&f" + loreLine;

        setLoreLine(heldItem, lineNumberAsInteger, loreLine);
        sendMessage(player, "&eLore updated");

    }

    private void setLoreLine(ItemStack item, int lineNumber, String lineContent) {

        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        if (meta.hasLore())
            lore = meta.getLore();

        boolean lineNumberIsGreaterThanListSize = lineNumber > lore.size();

        if (lineNumberIsGreaterThanListSize) {

            int emptyLinesToAdd = lineNumber - lore.size() - 1;

            for (int i = 0; i < emptyLinesToAdd; i++)
                lore.add("");

            lore.add(format(lineContent));
        } else {
            lore.set(lineNumber - 1, format(lineContent));
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    @Override
    public String getDescription() {
        return "Set the content of the lore at a specific line number";
    }

    @Override
    public String getUsage() {
        return "/is lore set <lineNumber> \"text\"";
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {

        if (args.length == 3)
            return List.of("lineNumber");

        if (args.length == 4)
            return List.of("\"text\"");

        return new ArrayList<>();
    }
}
