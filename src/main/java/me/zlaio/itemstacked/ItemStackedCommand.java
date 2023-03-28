package me.zlaio.itemstacked;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.zlaio.itemstacked.commands.*;
import me.zlaio.itemstacked.commands.datacommands.DataSubCommand;
import me.zlaio.itemstacked.commands.lorecommands.LoreSubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.zlaio.itemstacked.ItemProvider;
import me.zlaio.itemstacked.YAMLFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemStackedCommand extends Command implements TabCompleter {

    private final ItemProvider itemProvider;
    private final YAMLFile itemFile;
    private final JavaPlugin plugin;

    public ItemStackedCommand(ItemProvider itemProvider, JavaPlugin plugin, YAMLFile itemFile) {
        this.itemProvider = itemProvider;
        this.itemFile = itemFile;
        this.plugin = plugin;

        loadSubCommands();
    }

    private void loadSubCommands() {
        addSubCommand("saveitem", new SaveItemSubCommand(itemProvider));
        addSubCommand("reload", new ReloadSubCommand(ItemStacked.getInstance()));
        addSubCommand("setdisplayname", new SetDisplayNameSubCommand());
        addSubCommand("lore", new LoreSubCommand());
        addSubCommand("give", new GiveSubCommand(itemProvider, itemFile));
        addSubCommand("delete", new DeleteItemSubCommand(itemProvider));
        addSubCommand("data", new DataSubCommand(plugin));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length < 1) {
            sendAllSubCommandSnippets(player);
            return;
        }

        String subCommand = args[0];

        if (!isSubCommand(subCommand)) {
            sendMessage(player, "&cUnknown command run /is to view all available commands");
            return;
        }

        runSubCommand(subCommand, sender, args);
    }

    @Override
    public void sendAllSubCommandSnippets(Player player) {
        player.sendMessage(format("&e-----" + COMMAND_PREFIX + "-----"));
        player.sendMessage(format("&7[] &f- &eoptional parameter"));
        player.sendMessage(format("&7<> &f- &erequired parameter"));

        for (Map.Entry<String, SubCommand> entry : getSubCommandEntrySet()) {
            SubCommand subCommand = entry.getValue();
            player.sendMessage(format("&e" + subCommand.getUsage() + " &f- &7" + subCommand.getDescription()));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        if (! (sender instanceof Player))
            return new ArrayList<>();

        //TODO: Add permission check

        if (args.length == 1)
            return new ArrayList<>(getSubCommandNames());

        String subCommand = args[0];

        if (!isSubCommand(subCommand))
            return new ArrayList<>();

        return getTabCompletions(subCommand, sender, args);
    }

}
