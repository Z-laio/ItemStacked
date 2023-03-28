package me.zlaio.itemstacked.commands.datacommands;

import me.zlaio.itemstacked.commands.SubCommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SetCustomModelDataSubCommand extends SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length < 4) {
            sendMessage(player, "&cUsage: &e" + getUsage());
            return;
        }

        ItemStack heldItem = player.getInventory().getItemInHand();

        if (heldItem.getType() == Material.AIR) {
            sendMessage(player, "&cPlease hold the item you wish to set data on");
            return;
        }

        int customModelData;

        try {
            customModelData = Integer.parseInt(args[3]);
        } catch (NumberFormatException exception) {
            sendMessage(player, "&cThe custom model data value must be an integer");
            return;
        }

        ItemMeta meta = heldItem.getItemMeta();
        meta.setCustomModelData(customModelData);
        heldItem.setItemMeta(meta);

        sendMessage(player, "&eCustom model data set");
    }

    @Override
    public String getDescription() {
        return "Sets the custom model data on an item";
    }

    @Override
    public String getUsage() {
        return "/is data set custom_model_data <value>";
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {

        if (args.length == 4)
            return List.of("value");

        return new ArrayList<>();
    }
}
