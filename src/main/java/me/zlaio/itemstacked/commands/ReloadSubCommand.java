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
        sendMessage(player, "&e'&7" + itemFile.getName() + "&e' &7has been reloaded");
    }

    @Override
    public String getCommandSnippet() {
        return "reload - reloads the file used to save the item";
    }
    
}
