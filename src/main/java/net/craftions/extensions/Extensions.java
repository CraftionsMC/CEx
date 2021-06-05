package net.craftions.extensions;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Extensions extends JavaPlugin {

    @Override
    public void onEnable() {
        if(!Bukkit.getPluginManager().isPluginEnabled("CraftionsApi")){
            System.err.println("Please download and install CraftionsApi from https://github.com/MCTzOCK/CraftionsAPI/releases in order to run Craftions Extensions");
            return;
        }
    }
}
