/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.extensions.command;

import net.craftions.api.networking.NetUtils;
import net.craftions.extensions.ExtensionManager;
import net.craftions.extensions.Extensions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CommandCex implements CommandExecutor {

    public static String prefix = "[§9CEx§r] ";
    protected String[] subCommands = {"install", "remove", "reinstall"};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(getHelpMessage("", true));
        }else {
            args[1] = args[1].toLowerCase();
            switch (args[0].toLowerCase()){
                case "install":
                    if(checkForMinimalArgs(2, sender, args)){
                        String packageName = args[1];
                        ExtensionManager.installPackage(packageName, sender);
                    }
                    break;
                case "remove":
                    if(checkForMinimalArgs(2, sender, args)) {
                        String packageName = args[1];
                        ExtensionManager.removePackage(packageName, sender);
                    }
                    break;
                case "reinstall":
                    if(checkForMinimalArgs(2, sender, args)){
                        String packageName = args[1];
                        ExtensionManager.removePackage(packageName, sender);
                        Extensions.c.put("startup", "pkgToDownload", packageName);
                        try {
                            Extensions.c.store();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Bukkit.reload();
                    }
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
                r.append("§7Usage: §c/cex install <name_of_package>\n");
                break;
            case "remove":
                r.append("§7⇛ §c/cex remove §7removes a package\n");
                r.append("§7Usage: §c/cex remove <name_of_package>\n");
                break;
            case "reinstall":
                r.append("§7⇛ §c/cex reinstall §7reinstalls an installed packages\n");
                r.append("§7Usage: §c/cex reinstall <name_of_package>\n");
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
