package me.zlaio.itemstacked.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.zlaio.itemstacked.ItemProvider;

public class SaveItemSubCommand extends SubCommand {

    private final ItemProvider itemProvider;

    public SaveItemSubCommand(ItemProvider itemProvider) {
        this.itemProvider = itemProvider;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        
        Player player = (Player) sender;
        
        if (args.length < 2) {
            sendMessage(player, "&c/itemstacked saveitem <itemName>");
            return;
        }

        ItemStack heldItem = player.getInventory().getItemInHand();

        if (heldItem.getType() == Material.AIR) {
            sendMessage(player, "&cHold the item you wish to be saved");
            return;
        }

        String itemToBeSavedName = args[1];

        boolean anItemIsAlreadySavedWithThisName = itemProvider.getItem(itemToBeSavedName) != null;

        if (anItemIsAlreadySavedWithThisName) {

            if (args.length < 3) {
                sendMessage(player,
                        "&cAn item with the name &e'&7"
                                + itemToBeSavedName
                                + "&e'&c to override this, run the command with an additional"
                                + "argument of &e'&7-o&e'");
                return;
            }

            String flagArgument = args[2];

            if (flagArgument.equals("-o")) {

                sendMessage(player, "&eSave for &7'&e" + itemToBeSavedName + "&7'&e has been overidden");
                itemProvider.saveItem(itemToBeSavedName, heldItem);

            } else {
                sendMessage(player, "&cTo override the save, use &e'&7-o&e'");
            }

            return;
        }

        sendMessage(player, "&Saved item &7'&e" + itemToBeSavedName.toLowerCase() + "&7'");
        itemProvider.saveItem(itemToBeSavedName, heldItem);
    }

    @Override
    public String getCommandSnippet() {
        return "saveitem - Saves an item held by the player to a file";
    }

}
