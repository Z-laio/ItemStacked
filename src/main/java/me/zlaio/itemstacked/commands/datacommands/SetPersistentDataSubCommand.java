package me.zlaio.itemstacked.commands.datacommands;

import me.zlaio.itemstacked.commands.SubCommand;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SetPersistentDataSubCommand extends SubCommand  {

    private final JavaPlugin plugin;

    public SetPersistentDataSubCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length < 4) {
            sendMessage(player, "&cUsage: &e" + getUsage());
            return;
        }

        ItemStack heldItem = player.getInventory().getItemInHand();

        if (heldItem.getType() == Material.AIR) {
            sendMessage(player, "&cPlease hold the item you wish to set data on of");
            return;
        }

        String key = args[3];
        String value = args[4];

        setData(heldItem, key, value);
        sendMessage(player, "&eData set");
    }

    private void setData(ItemStack item, String key, String value) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
        item.setItemMeta(meta);
    }

    @Override
    public String getDescription() {
        return "Stores persistent data on an item";
    }

    @Override
    public String getUsage() {
        return "/is data set pdc <key> <value>";
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {

        if (args.length == 4)
            return List.of("key");

        if (args.length == 5)
            return List.of("value");

        return new ArrayList<>();
    }
}
