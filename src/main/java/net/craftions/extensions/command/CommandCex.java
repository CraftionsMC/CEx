/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.extensions.command;

import net.craftions.api.networking.NetUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandCex implements CommandExecutor {

    protected String prefix = "[§9CEx§r] ";
    protected String[] subCommands = {"install", "remove", "update", "upgrade", "info", "help"};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(getHelpMessage("", true));
        }else {
            switch (args[0].toLowerCase()){
                case "install":
                    if(checkForMinimalArgs(2, sender, args)){
                        String packageName = args[1];
                        String packageVersion = "latest";
                        if(args.length == 3){
                            packageVersion = args[2];
                        }
                    }
                    break;
                case "remove":
                    break;
                case "update":
                    break;
                case "upgrade":
                    break;
                case "info":
                    break;
                case "help":
                    if (args.length == 1){
                        getHelpMessage("", true);
                    }else {
                        getHelpMessage(args[0], true);
                    }
                    break;
                default:
                    sender.sendMessage(getHelpMessage("", true));
                    break;
            }
        }
        return true;
    }

    public boolean checkForMinimalArgs(int min, CommandSender sender, String[] args){
        if(args.length >= min){
            return true;
        }else {
            sender.sendMessage(prefix + "§7You need to specify at least §c" + min + " §7arguments");
            return false;
        }
    }

    protected String getHelpMessage(String subCommand, Boolean withPrefix) {
        StringBuilder r = new StringBuilder();
        if (withPrefix) {
            r = new StringBuilder("§9CEx §eHelp\n§r");
        }
        switch (subCommand.toLowerCase()) {
            case "install":
                r.append("§7⇛ §c/cex install §7installs a package\n");
                r.append("§7Usage: §c/cex install <name_of_package> [version]\n");
                break;
            case "remove":
                r.append("§7⇛ §c/cex remove §7removes a package\n");
                r.append("§7Usage: §c/cex remove <name_of_package>\n");
                break;
            case "update":
                r.append("§7⇛ §c/cex update §7updates the repository cache\n");
                r.append("§7Usage: §c/cex update\n");
                break;
            case "upgrade":
                r.append("§7⇛ §c/cex upgrade §7updates all installed packages\n");
                r.append("§7Usage: §c/cex upgrade\n");
                break;
            case "info":
                r.append("§7⇛ §c/cex info §7shows information about one package\n");
                r.append("§7Usage: §c/cex info <name_of_package>\n");
                break;
            case "help":
                r.append("§7⇛ §c/cex help §7shows help to a sub-command\n");
                r.append("§7Usage: §c/cex help <sub_command>\n");
                break;
            default:
                for(String s : subCommands){
                    r.append(getHelpMessage(s, false)).append("\n");
                }
                break;
        }
        return r.toString();
    }
}
