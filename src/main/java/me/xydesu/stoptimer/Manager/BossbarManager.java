package me.xydesu.stoptimer.Manager;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.plugin.Plugin;

public class BossbarManager {

    private BossBar bossbar;
    private MessageManager message;
    private Manager manager;
    private ConfigManager config;

    public BossbarManager(Plugin plugin, Manager Manager, ConfigManager config) {
        this.message = new MessageManager(plugin, config.getLanguage());
        this.manager = Manager;
        this.config = config;
    }

    //create a bossbar when the countdown starts
    public void createBossbar() {
        NamespacedKey key = new NamespacedKey("stoptimer", "countdown");
        bossbar = Bukkit.createBossBar(key, message.getBossbarMessage(manager.getTimeLeft()), BarColor.valueOf(config.getBossbarColor()), BarStyle.valueOf(config.getBossbarStyle()));
        double progress = 1.0;
        if (manager.getTimeMax() > 0) {
            progress = Math.max(0.0, Math.min(1.0, (double) manager.getTimeLeft() / manager.getTimeMax()));
        }
        bossbar.setProgress(progress);
    }

    public void updateBossbar() {
        bossbar.setTitle(message.getBossbarMessage(manager.getTimeLeft()));
        double progress = 1.0;
        if (manager.getTimeMax() > 0) {
            progress = Math.max(0.0, Math.min(1.0, (double) manager.getTimeLeft() / manager.getTimeMax()));
        }
        bossbar.setProgress(progress);
    }

    public void showBossbar() {
        Bukkit.getOnlinePlayers().forEach(bossbar::addPlayer);
    }

    public void hideBossbar() {
        Bukkit.getOnlinePlayers().forEach(bossbar::removePlayer);
    }

    public void removeBossbar() {
        if (bossbar != null) {
            Bukkit.removeBossBar(new NamespacedKey("stoptimer", "countdown"));
            bossbar = null;
        }
    }

    // 新增：切換語言時刷新 Bossbar 顯示與屬性
    public void reloadLanguage(String language) {
        this.message = new MessageManager(Bukkit.getPluginManager().getPlugin("StopTimer"), language);
        if (bossbar != null) {
            // 重新建立 Bossbar 以套用新語言和新屬性
            NamespacedKey key = new NamespacedKey("stoptimer", "countdown");
            Bukkit.removeBossBar(key);
            bossbar = Bukkit.createBossBar(
                key,
                message.getBossbarMessage(manager.getTimeLeft()),
                BarColor.valueOf(config.getBossbarColor()),
                BarStyle.valueOf(config.getBossbarStyle())
            );
            double progress = 1.0;
            if (manager.getTimeMax() > 0) {
                progress = Math.max(0.0, Math.min(1.0, (double) manager.getTimeLeft() / manager.getTimeMax()));
            }
            bossbar.setProgress(progress);
            // 重新顯示給所有玩家
            showBossbar();
        }
    }
}