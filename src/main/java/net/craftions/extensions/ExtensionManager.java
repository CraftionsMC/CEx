/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.extensions;

import net.craftions.api.networking.NetUtils;
import net.craftions.extensions.command.CommandCex;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ini4j.Ini;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.UUID;

public class ExtensionManager {

    public static final String repositoryURL = "https://cex.craftions.net/repo/pkg/";
    public static final String tempDirectoryName = ".plugin_temp";


    public static void installPackage(String name, CommandSender sender) {
        if(sender == null)
            sender = Bukkit.getConsoleSender();
        try {
            UUID uuid = UUID.randomUUID();
            checkTemp();
            NetUtils.download(repositoryURL + name + "/info.ini", new File(tempDirectoryName + "/info-" + uuid + ".ini"));
            Ini ini = new Ini(new File(tempDirectoryName + "/info-" + uuid + ".ini"));
            String version = ini.get("package", "currentVersion");
            Files.copy(new File(tempDirectoryName + "/info-" + uuid + ".ini").toPath(), new File("plugins/info-" + uuid + ".ini").toPath());

            NetUtils.download(repositoryURL + name + "/" + version + ".ini", new File(tempDirectoryName + "/" + name + ".ini"));
            ini = new Ini(new File(tempDirectoryName + "/" + name + ".ini"));
            String pluginURL = ini.get("package", "downloadURL");
            NetUtils.download(pluginURL, new File("plugins/" + name + ".jar"));
            sender.sendMessage(CommandCex.prefix + "§7Successfully downloaded plugin! The plugin will be activated after the next server §creload/restart§7!");
        } catch (Exception ex) {
            sender.sendMessage(CommandCex.prefix + "§7The package §c" + name + " §7does not exists");
        }
    }

    public static void removePackage(String name, CommandSender sender) {
        if(sender == null)
            sender = Bukkit.getConsoleSender();
        try {
            Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin(name));
        } catch (Exception ignored) {
        }
        File jarFile = new File("plugins/" + name + ".jar");
        File iniFile = new File("plugins/" + name + ".ini");
        if (jarFile.exists()) {
            jarFile.delete();
        }
        if (iniFile.exists()) {
            iniFile.delete();
        }
        sender.sendMessage(CommandCex.prefix + "§7The plugin was §csuccessfully §7removed!");
    }

    public static Boolean doesVersionExist(String name, String version) {
        try {
            NetUtils.download(repositoryURL + name + "/" + version + "/" + version + ".ini", new File(tempDirectoryName + "/" + version + "-" + UUID.randomUUID() + ".ini"));
            new File(tempDirectoryName + "/info-" + UUID.randomUUID() + ".ini").delete();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static void checkTemp() {
        File tempDir = new File(tempDirectoryName);
        if (!tempDir.isDirectory()) {
            tempDir.mkdirs();
        }
    }
}
