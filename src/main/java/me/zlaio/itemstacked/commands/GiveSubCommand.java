package me.zlaio.itemstacked.commands;

import me.zlaio.itemstacked.ItemProvider;
import me.zlaio.itemstacked.YAMLFile;
import me.zlaio.itemstacked.exceptions.InvalidItemConfigurationException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GiveSubCommand extends SubCommand {

    private final ItemProvider itemProvider;
    private final YAMLFile itemFile;

    public GiveSubCommand(ItemProvider itemProvider, YAMLFile itemFile) {
        this.itemProvider = itemProvider;
        this.itemFile = itemFile;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length < 2) {
            sendMessage(player, "&cUsage: &e" + getUsage());
            return;
        }

        //Giving to other player
        if (args.length > 2) {

            String playerName = args[1];
            String itemName = args[2];

            Player targetPlayer = Bukkit.getPlayer(playerName);

            if (targetPlayer == null) {
                sendMessage(player, "&cCouldn't find that player, are they online?");
                return;
            }

            ItemStack itemToGive;
            try {
                itemToGive = itemProvider.getItem(itemName);
            } catch (InvalidItemConfigurationException e) {
                sendMessage(player, String.format("&cThe 'material' key for this item in '%s' is invalid", itemFile.getName()));
                return;
            }

            if (itemToGive == null) {
                sendMessage(player, String.format("&cCouldn't find that item in '%s'", itemFile.getName()));
                return;
            }

            targetPlayer.getInventory().addItem(itemToGive);
            sendMessage(player, "&eGave &7" + playerName + " &e" + itemName);

        //Giving to self
        } else {

            String itemName = args[1];
            ItemStack itemToGive = itemProvider.getItem(itemName);

            if (itemToGive == null) {
                sendMessage(player, String.format("&cCouldn't find that item in '%s'", itemFile.getName()));
                return;
            }

            player.getInventory().addItem(itemToGive);
            sendMessage(player, "&eReceived &7" + itemName);

        }

    }

    @Override
    public String getDescription() {
        return "Gives an item from the save file";
    }

    @Override
    public String getUsage() {
        return "/is give [player] <itemName>";
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {

        if (args.length == 2)
            return itemProvider.getAllSavedItemNames();

        return new ArrayList<>();
    }
}
