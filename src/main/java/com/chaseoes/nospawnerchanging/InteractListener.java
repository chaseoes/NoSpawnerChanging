package com.chaseoes.nospawnerchanging;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.hasBlock() && event.getClickedBlock().getType() == Material.MOB_SPAWNER && player.getItemInHand() != null) {
            if (player.getItemInHand().getType() == Material.MONSTER_EGG) {
                RestrictionMode mode = NoSpawnerChanging.getInstance().getRestrictionMode();
                if (!mode.allowed(event.getPlayer())) {
                    event.setCancelled(true);
                    event.setUseInteractedBlock(Result.DENY);
                    event.setUseItemInHand(Result.DENY);
                }
            }
        }
    }

}
