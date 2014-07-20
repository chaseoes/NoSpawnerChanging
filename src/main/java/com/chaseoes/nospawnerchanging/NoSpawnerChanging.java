package com.chaseoes.nospawnerchanging;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class NoSpawnerChanging extends JavaPlugin {

    private static NoSpawnerChanging instance;
    public static final String PREFIX = ChatColor.YELLOW + "[NoSpawnerChanging] " + ChatColor.GRAY;

    public static NoSpawnerChanging getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
        getConfig().options().header("http://dev.bukkit.org/bukkit-plugins/nospawnerchanging/ #");
        getConfig().options().copyHeader(true);
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void onDisable() {
        reloadConfig();
        saveConfig();
        instance = null;
    }

    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (strings.length == 0) {
            cs.sendMessage(PREFIX + "Version " + getDescription().getVersion() + " by " + getDescription().getAuthors().get(0) + ".");
            if (cs.hasPermission(getDescription().getName().toLowerCase() + ".admin")) {
                cs.sendMessage(PREFIX + "Reload configuration: /" + string + " reload");
                cs.sendMessage(PREFIX + "Switch modes: /" + string + " mode <permission|creative>");
            }
            return true;
        }

        if (strings[0].equalsIgnoreCase("reload")) {
            if (cs.hasPermission(getDescription().getName().toLowerCase() + ".admin")) {
                reloadConfig();
                saveConfig();
                cs.sendMessage(PREFIX + "Successfully reloaded the configuration!");
            } else {
                cs.sendMessage(PREFIX + "You don't have permission to do that.");
            }
            return true;
        }

        if (strings[0].equalsIgnoreCase("mode")) {
            if (cs.hasPermission(getDescription().getName().toLowerCase() + ".admin")) {
                if (strings.length == 2) {
                    RestrictionMode mode = RestrictionMode.get(strings[1]);
                    if (mode != null) {
                        getConfig().set("restriction-mode", mode.getName().toUpperCase());
                        saveConfig();
                        cs.sendMessage(PREFIX + "Switched to " + mode.getName() + "!");
                    } else {
                        cs.sendMessage(PREFIX + "Usage: /" + string + " mode <permission|creative>");
                    }
                } else {
                    cs.sendMessage(PREFIX + "Usage: /" + string + " mode <permission|creative>");
                }
            } else {
                cs.sendMessage(PREFIX + "You don't have permission to do that.");
            }
        } else {
            cs.sendMessage(PREFIX + "Unknown command. Type " + ChatColor.RED + "/" + string + ChatColor.GRAY + " for help.");
        }
        return true;
    }

    public RestrictionMode getRestrictionMode() {
        return RestrictionMode.get(getConfig().getString("restriction-mode"));
    }

}
