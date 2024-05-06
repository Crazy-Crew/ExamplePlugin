package com.ryderbelserion.example;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import us.crazycrew.crazycrates.CratesProvider;
import us.crazycrew.crazycrates.api.enums.types.KeyType;
import us.crazycrew.crazycrates.api.users.UserManager;
import us.crazycrew.crazycrates.platform.IServer;
import java.util.UUID;

public class ExamplePlugin extends JavaPlugin implements Listener {

    public boolean isPluginEnabled(String pluginName) {
        return getServer().getPluginManager().isPluginEnabled(pluginName);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        if (isPluginEnabled("CrazyCrates")) {
            Plugin instance = getServer().getPluginManager().getPlugin("CrazyCrates");

            if (instance != null) {
                getLogger().warning("The plugin: " + instance.getName() + " is enabled.");

                getServer().getPluginManager().registerEvents(this, this);
            }
        } else {
            getLogger().warning("Could not find CrazyCrates!");

            // only disable the plugin if you absolutely require CrazyCrates
            //getServer().getPluginManager().disablePlugin(this);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        UUID uuid = player.getUniqueId();

        @NotNull IServer service = CratesProvider.get();

        UserManager userManager = service.getUserManager();

        userManager.addKeys(uuid, "CrateExample", KeyType.virtual_key, 5);
        userManager.addKeys(uuid, "QuadCrateExample", KeyType.physical_key, 10);
        userManager.addKeys(uuid, "Fuckyou", KeyType.virtual_key, 1);
        userManager.addOpenedCrate(uuid, "CrateExample");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}