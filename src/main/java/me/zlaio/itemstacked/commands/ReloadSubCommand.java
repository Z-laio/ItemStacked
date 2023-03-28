package me.zlaio.itemstacked.commands;

import me.zlaio.itemstacked.ItemStacked;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReloadSubCommand extends SubCommand {

    private final ItemStacked plugin;

    public ReloadSubCommand(ItemStacked plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        plugin.reload();
        sendMessage(player, "&ePlugin reloaded");
    }

    @Override
    public String getDescription() {
        return "Reloads the plugin/configuration";
    }

    @Override
    public String getUsage() {
        return "/is reload";
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

}
