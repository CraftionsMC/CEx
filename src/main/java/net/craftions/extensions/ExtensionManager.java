/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.extensions;

import org.bukkit.command.CommandSender;

import static net.craftions.api.networking.NetUtils.httpGet;

public class ExtensionManager {

    public static final String repositoryURL = "https://extensions.minecraft.craftions.net/repo/";

    public static void update(){

    }

    public static void installPackage(String name, CommandSender sender){

    }

    public static void removePackage(String name, CommandSender sender){

    }

    public static void upgradePackage(String name, CommandSender sender){

    }

    public static String getPackageInfo(String name){
        return "";
    }

    public static Boolean doesVersionExist(String name, String version){
        try {
            httpGet(repositoryURL + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
