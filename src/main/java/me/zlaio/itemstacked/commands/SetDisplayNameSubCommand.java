package me.zlaio.itemstacked.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetDisplayNameSubCommand extends SubCommand {

    @Override
    public String getCommandSnippet() {
        return "setdisplayname - Sets the display name of the player held item";
    }

    // /is setdisplayname ""

    @Override
    public void execute(CommandSender sender, String[] args) {
        
        Player player = (Player) sender;

        if (args.length < 3) {
            sendMessage(player, "&c/is setdisplayname \"name\"");
            return;
        }

        String displayName = args[2];

        if (!argumentHasValidQuotations(displayName)) {
            sendMessage(player, "&cWrap the new display name in quotes | \"name\"");
            return;
        }

        displayName = getContentBetweenQuotes(displayName);
        ItemStack heldItem = player.getInventory().getItemInHand();

        setDisplayName(displayName, heldItem);
        
        sendMessage(player, "&eDisplay name updated");
    }

    private void setDisplayName(String displayName, ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(format(displayName));
        item.setItemMeta(itemMeta);
    }

    private String getContentBetweenQuotes(String argument) {
        return argument.substring(1, argument.length() - 1);
    }

    private boolean argumentHasValidQuotations(String argument) {
        boolean isWrappedInQuotes = 
                argument.substring(0, 1).equals("\"")
             && argument.substring(argument.length() - 2).equals("\"");
         
        return isWrappedInQuotes;
    }
    
}
