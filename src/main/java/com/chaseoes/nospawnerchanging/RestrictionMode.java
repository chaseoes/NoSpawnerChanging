package com.chaseoes.nospawnerchanging;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public enum RestrictionMode {

    PERMISSION, CREATIVE;

    public String getName() {
        return this.toString().toLowerCase();
    }

    public boolean allowed(Player player) {
        switch (this) {
        case PERMISSION:
            if (!player.hasPermission(NoSpawnerChanging.getInstance().getDescription().getName().toLowerCase() + ".bypass")) {
                player.sendMessage(NoSpawnerChanging.PREFIX + "You don't have permission to do that!");
                return false;
            }
        case CREATIVE:
            if (player.getGameMode() != GameMode.CREATIVE) {
                player.sendMessage(NoSpawnerChanging.PREFIX + "You must be in creative mode to do that!");
                return false;
            }
        default:
            return false;
        }
    }

    public static RestrictionMode get(String modeString) {
        try {
            RestrictionMode mode = valueOf(modeString.trim().toUpperCase());
            return mode;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
