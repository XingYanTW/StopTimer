package me.xydesu.stoptimer;

import me.xydesu.stoptimer.Manager.Manager;
import me.xydesu.stoptimer.Manager.PlaceholderManager;
import me.xydesu.stoptimer.Commands.StopServer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    // implement plugin
    private static Main instance;
    private Manager manager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();

        instance = this;

        manager = new Manager(this);
        StopServer stopServerCommand = new StopServer(manager);

        // Register commands
        getCommand("stopserver").setExecutor(stopServerCommand);
        getCommand("stopserver").setTabCompleter(stopServerCommand);

        new PlaceholderManager(this, manager).register();

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
