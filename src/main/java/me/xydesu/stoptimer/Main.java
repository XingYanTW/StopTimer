package me.xydesu.stoptimer;

import me.xydesu.stoptimer.Manager.BossbarManager;
import me.xydesu.stoptimer.Manager.ConfigManager;
import me.xydesu.stoptimer.Manager.Manager;
import me.xydesu.stoptimer.Manager.MessageManager;
import me.xydesu.stoptimer.Manager.PlaceholderManager;
import me.xydesu.stoptimer.Commands.StopServer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

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

        String[] languages = {"en_us", "zh_tw"};
        for (String lang : languages) {
            File file = new File(getDataFolder(), "Message." + lang + ".yml");
            if (!file.exists()) {
                saveResource("Message." + lang + ".yml", false);
            }
        }

        configManager = new ConfigManager(getConfig());
        messageManager = new MessageManager(this, configManager.getLanguage());
        manager = new Manager(this, messageManager, configManager);
        bossbarManager = new BossbarManager(this, manager, configManager);

        StopServer stopServerCommand = new StopServer(manager, messageManager, configManager);
        getCommand("stopserver").setExecutor(stopServerCommand);
        getCommand("stopserver").setTabCompleter(stopServerCommand);

        new PlaceholderManager(this, manager, configManager).register();
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

    public static Main getInstance() {
        return instance;
    }

    // 例如在 reload 指令或配置重载后，添加如下调用（请根据你的 reload 逻辑插入）：
    public void reloadConfigAndLanguage() {
        // 先取消旧倒计时和 Bossbar，防止旧任务残留
        if (manager != null) manager.cancelCountdown();
        if (bossbarManager != null) bossbarManager.removeBossbar();

        reloadConfig();
        configManager.reload(getConfig());
        String newLang = configManager.getLanguage();
        messageManager = new MessageManager(this, newLang);
        manager = new Manager(this, messageManager, configManager);
        bossbarManager = new BossbarManager(this, manager, configManager);

        // 重新注册指令和 Placeholder
        StopServer stopServerCommand = new StopServer(manager, messageManager, configManager);
        getCommand("stopserver").setExecutor(stopServerCommand);
        getCommand("stopserver").setTabCompleter(stopServerCommand);

        new PlaceholderManager(this, manager, configManager).register();
    }
}