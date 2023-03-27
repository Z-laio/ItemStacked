package me.zlaio.itemstacked.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.zlaio.itemstacked.commands.lorecommands.LoreSubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.zlaio.itemstacked.ItemProvider;
import me.zlaio.itemstacked.YAMLFile;

public class ItemStackedCommand extends Command implements TabCompleter {

    private final ItemProvider itemProvider;
    private final YAMLFile itemFile;
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public ItemStackedCommand(ItemProvider itemProvider, YAMLFile itemFile) {
        this.itemProvider = itemProvider;
        this.itemFile = itemFile;

        loadSubCommands();
    }

    private void loadSubCommands() {
        addSubCommand("saveitem", new SaveItemSubCommand(itemProvider));
        addSubCommand("reload", new ReloadSubCommand(itemFile));
        addSubCommand("setdisplayname", new SetDisplayNameSubCommand());
        addSubCommand("lore", new LoreSubCommand());
        addSubCommand("give", new GiveSubCommand(itemProvider));
        addSubCommand("delete", new DeleteItemSubCommand(itemProvider));
    }

    private void addSubCommand(String subCommand, SubCommand executor) {
        subCommands.put(subCommand, executor);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length < 1) {
            sendAllSubCommandSnippets(player);
            return;
        }

        String arg1 = args[0];

        if (!hasSubCommand(arg1)) {
            sendMessage(player, "&cUnknown command run /is to view all available commands");
            return;
        }

        runSubCommand(arg1, sender, args);

    }

    private boolean hasSubCommand(String subCommand) {
        return subCommands.containsKey(subCommand);
    }

    private void runSubCommand(String subCommand, CommandSender sender, String[] args) {
        subCommands.get(subCommand).execute(sender, args);
    }

    private void sendAllSubCommandSnippets(Player player) {
        for (SubCommand subCommand : subCommands.values()) {
            player.sendMessage(format("&e" + subCommand.getUsage() + " &f- &7" + subCommand.getDescription()));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        if (! (sender instanceof Player))
            return new ArrayList<>();

        //TODO: Add permission check

        if (args.length == 1)
            return new ArrayList<>(subCommands.keySet());

        String subCommand = args[0];

        if (!hasSubCommand(subCommand))
            return new ArrayList<>();

        return subCommands.get(subCommand).getTabCompletions(sender, args);
    }

}
