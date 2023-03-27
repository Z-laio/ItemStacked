package me.zlaio.itemstacked.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.zlaio.itemstacked.ItemProvider;

import java.util.ArrayList;
import java.util.List;

public class SaveItemSubCommand extends SubCommand {

    private final ItemProvider itemProvider;

    public SaveItemSubCommand(ItemProvider itemProvider) {
        this.itemProvider = itemProvider;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        
        Player player = (Player) sender;
        
        if (args.length < 2) {
            sendMessage(player, "&cUsage: &e" + getUsage());
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
                        "&cAn item with the name &7'&e" + itemToBeSavedName + "&7' &chas already been saved, "
                                + "to override this, run the command &7'&e/is saveitem " + itemToBeSavedName + " -o&7'");
                return;
            }

            String flagArgument = args[2];

            if (flagArgument.equals("-o")) {

                sendMessage(player, "&eSave for &7'&e" + itemToBeSavedName + "&7'&e has been overridden");
                itemProvider.saveItem(itemToBeSavedName, heldItem);

            } else {
                sendMessage(player, "&cTo override the save, use &7'&e-o&7'");
            }

            return;
        }

        sendMessage(player, "&eSaved item &7'&e" + itemToBeSavedName.toLowerCase() + "&7'");
        itemProvider.saveItem(itemToBeSavedName, heldItem);
    }

    @Override
    public String getDescription() {
        return "Saves the item the player is holding to a file";
    }

    @Override
    public String getUsage() {
        return "/is saveitem <itemName> [-o]";
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {

        if (args.length == 2)
            return List.of("itemName");

        return new ArrayList<>();
    }
}
