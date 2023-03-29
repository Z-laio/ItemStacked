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
import java.util.stream.Collector;

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

        if (args.length < 4) {
            sendMessage(player, "&cUsage: &e" + getUsage());
            return;
        }

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

        int amount;
        try {
            amount = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            sendMessage(player, "&cThe amount must be an integer");
            return;
        }

        for (int i = 0; i < amount; i++)
            targetPlayer.getInventory().addItem(itemToGive);

        sendMessage(player, "&eGave &7" + playerName + " &e" + itemName);
    }

    @Override
    public String getDescription() {
        return "Gives an item from the save file";
    }

    @Override
    public String getUsage() {
        return "/is give <player> <itemName> <amount>";
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {

        if (args.length == 2)
            return getOnlinePlayerNames();

        if (args.length == 3)
            return itemProvider.getAllSavedItemNames();

        return new ArrayList<>();
    }

    private List<String> getOnlinePlayerNames() {
        List<String> names = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers())
            names.add(player.getName());

        return names;
    }

}
