package me.xydesu.stoptimer;

import me.xydesu.stoptimer.Manager.BossbarManager;
import me.xydesu.stoptimer.Manager.Manager;
import me.xydesu.stoptimer.Manager.MessageManager;
import me.xydesu.stoptimer.Manager.PlaceholderManager;
import me.xydesu.stoptimer.Commands.StopServer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    // implement plugin
    private static Main instance;
    private Manager manager;
    private MessageManager messageManager;
    private BossbarManager bossbarManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();

        instance = this;

        messageManager = new MessageManager(getConfig());
        manager = new Manager(this, messageManager);
        bossbarManager = new BossbarManager(this, manager);
        StopServer stopServerCommand = new StopServer(manager, messageManager);

        // Register commands
        getCommand("stopserver").setExecutor(stopServerCommand);
        getCommand("stopserver").setTabCompleter(stopServerCommand);

        new PlaceholderManager(this, manager).register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public static Plugin getInstance() {
        return instance;
    }
}
