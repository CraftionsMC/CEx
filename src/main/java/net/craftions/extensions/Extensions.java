package net.craftions.extensions;

import net.craftions.extensions.command.CommandCex;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;

public final class Extensions extends JavaPlugin {

    public static Ini c;

    @Override
    public void onEnable() {
        if(!Bukkit.getPluginManager().isPluginEnabled("CraftionsApi")){
            System.err.println("Please download and install CraftionsApi from https://github.com/MCTzOCK/CraftionsAPI/releases in order to run Craftions Extensions");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        getCommand("cex").setExecutor(new CommandCex());
        File configFile = new File("cex.ini");
        if(!configFile.exists()){
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            c = new Ini(configFile);
            if(c.get("startup", "pkgToDownload") != null){
                ExtensionManager.installPackage(c.get("startup", "pkgToDownload"), null);
                c.remove("startup", "pkgToDownload");
                c.store();
                Bukkit.reload();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
