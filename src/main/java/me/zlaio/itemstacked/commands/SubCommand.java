package me.zlaio.itemstacked.commands;

public abstract class SubCommand extends Command{

    /***
     * @return A command description, usage, name etc. to display to the player
     */
    abstract public String getCommandSnippet();

}
