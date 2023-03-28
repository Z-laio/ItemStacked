package me.zlaio.itemstacked.commands;

import me.zlaio.itemstacked.CommandUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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

        String displayName = CommandUtils.getStringFromArgs(1, args);

        if (!CommandUtils.argumentHasValidQuotations(displayName)) {
            sendMessage(player, "&cWrap the new display name in quotes | \"name\"");
            return;
        }

        displayName = CommandUtils.getContentBetweenQuotes(displayName);

        setDisplayName(displayName, heldItem);
        
        sendMessage(player, "&eDisplay name updated");
    }

    private void setDisplayName(String displayName, ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(format(displayName));
        item.setItemMeta(itemMeta);
    }



    @Override
    public String getDescription() {
        return "Sets the display name of the current item held by the player";
    }

    @Override
    public String getUsage() {
        return "/is setdisplayname <\"text\">";
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {

        if (args.length == 2)
            return List.of("\"text\"");

        return new ArrayList<>();
    }
}
