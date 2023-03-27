package me.zlaio.itemstacked.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetDisplayNameSubCommand extends SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        
        Player player = (Player) sender;

        if (args.length < 2) {
            sendMessage(player, "&cUsage: &e" + getUsage());
            return;
        }

        ItemStack heldItem = player.getInventory().getItemInHand();

        if (heldItem.getType() == Material.AIR) {
            sendMessage(player, "&cPlease hold the item you wish to set the display name of");
            return;
        }

        String displayName = getNameFromArgs(args);

        if (!argumentHasValidQuotations(displayName)) {
            sendMessage(player, "&cWrap the new display name in quotes | \"name\"");
            return;
        }

        displayName = getContentBetweenQuotes(displayName);

        setDisplayName(displayName, heldItem);
        
        sendMessage(player, "&eDisplay name updated");
    }

    private void setDisplayName(String displayName, ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(format(displayName));
        item.setItemMeta(itemMeta);
    }

    private String getNameFromArgs(String[] args) {
        StringBuilder builder = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            builder.append(args[i]);

            if (i != args.length - 1)
                builder.append(" ");

        }

        return builder.toString();
    }

    private String getContentBetweenQuotes(String argument) {
        return argument.substring(1, argument.length() - 1);
    }

    private boolean argumentHasValidQuotations(String argument) {
        System.out.println(argument.charAt(0));
        System.out.println(argument.substring(argument.length() - 1));
        boolean isWrappedInQuotes =
                argument.substring(0, 1).equals("\"")
             && argument.substring(argument.length() - 1).equals("\"");

        return isWrappedInQuotes;
    }

    @Override
    public String getDescription() {
        return "Sets the display name of the current item held by the player";
    }

    @Override
    public String getUsage() {
        return "/is setdisplayname \"text\"";
    }
}
