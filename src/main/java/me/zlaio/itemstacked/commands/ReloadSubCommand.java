package me.zlaio.itemstacked.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.zlaio.itemstacked.YAMLFile;

public class ReloadSubCommand extends SubCommand{

    private final YAMLFile itemFile;

    public ReloadSubCommand(YAMLFile itemFile) {
        this.itemFile = itemFile;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        itemFile.reload();
        sendMessage(player, "&7'&e" + itemFile.getName() + "&7' &ehas been reloaded");
    }

    @Override
    public String getDescription() {
        return "Reloads the file that reads/writes ItemStacks";
    }

    @Override
    public String getUsage() {
        return "/is reload";
    }
}
