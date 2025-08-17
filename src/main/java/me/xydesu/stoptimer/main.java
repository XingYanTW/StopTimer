package me.xydesu.stoptimer;

import me.xydesu.stoptimer.commands.stopserver;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    // implement plugin
    private static main instance;
    private Manager manager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();

        instance = this;

        manager = new Manager(this);
        stopserver stopServerCommand = new stopserver(manager);

        // Register commands
        getCommand("stopserver").setExecutor(stopServerCommand);
        getCommand("stopserver").setTabCompleter(stopServerCommand);

        new Placeholder(this, manager).register();

        // configure files

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getInstance() {
        return instance;
    }
}
