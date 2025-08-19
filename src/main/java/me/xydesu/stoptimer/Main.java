package me.xydesu.stoptimer;

import me.xydesu.stoptimer.Manager.BossbarManager;
import me.xydesu.stoptimer.Manager.ConfigManager;
import me.xydesu.stoptimer.Manager.Manager;
import me.xydesu.stoptimer.Manager.MessageManager;
import me.xydesu.stoptimer.Manager.PlaceholderManager;
import me.xydesu.stoptimer.Commands.StopServer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;
    private Manager manager;
    private MessageManager messageManager;
    private ConfigManager configManager;
    private BossbarManager bossbarManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        messageManager = new MessageManager(getConfig());
        configManager = new ConfigManager(getConfig());
        manager = new Manager(this, messageManager, configManager);
        bossbarManager = new BossbarManager(this, manager, configManager);

        StopServer stopServerCommand = new StopServer(manager, messageManager, configManager);
        getCommand("stopserver").setExecutor(stopServerCommand);
        getCommand("stopserver").setTabCompleter(stopServerCommand);

        new PlaceholderManager(this, manager).register();
    }

    @Override
    public void onDisable() {
        if (manager != null) manager.cancelCountdown();
        if (bossbarManager != null) bossbarManager.removeBossbar();
        instance = null;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public static Plugin getInstance() {
        return instance;
    }
}