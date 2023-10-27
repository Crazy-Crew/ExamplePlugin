package com.ryderbelserion.example;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import us.crazycrew.crazycrates.api.CrazyCratesService;
import us.crazycrew.crazycrates.api.ICrazyCrates;
import us.crazycrew.crazycrates.api.enums.types.KeyType;
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
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        UUID uuid = player.getUniqueId();

        @NotNull ICrazyCrates service = CrazyCratesService.get();

        service.getUserManager().addKeys(5, uuid, "CrateExample", KeyType.virtual_key);
        service.getUserManager().addKeys(10, uuid, "QuadCrateExample", KeyType.physical_key);
        service.getUserManager().addKeys(1, uuid, "Fuckyou", KeyType.virtual_key);
        service.getUserManager().addOpenedCrate(uuid, "CrateExample");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}