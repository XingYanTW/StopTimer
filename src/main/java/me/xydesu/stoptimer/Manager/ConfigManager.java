package me.xydesu.stoptimer.Manager;

import org.bukkit.configuration.file.FileConfiguration;
import java.util.List;

public class ConfigManager {

    private FileConfiguration config;

    public ConfigManager(FileConfiguration config) {
        this.config = config;
    }

    public void reload(FileConfiguration newConfig) {
        this.config = newConfig;
    }

    // Bossbar.Enable
    public boolean getBossbarEnabled() {
        return config.getBoolean("Bossbar.Enable");
    }

    // Bossbar.Color
    public String getBossbarColor() {
        return config.getString("Bossbar.Color");
    }

    // Bossbar.Style
    public String getBossbarStyle() {
        return config.getString("Bossbar.Style");
    }

    // NotifyTime.Title.FirstRun
    public boolean getTitleFirstRun() {
        return config.getBoolean("NotifyTime.Title.FirstRun");
    }
    public List<Integer> getTitleSeconds() {
        return config.getIntegerList("NotifyTime.Title.Seconds");
    }

    // NotifyTime.Message.FirstRun
    public boolean getMessageFirstRun() {
        return config.getBoolean("NotifyTime.Message.FirstRun");
    }
    public List<Integer> getMessageSeconds() {
        return config.getIntegerList("NotifyTime.Message.Seconds");
    }

    // NotifyTime.Discord.Enable
    public boolean getDiscordEnabled() {
        return config.getBoolean("NotifyTime.Discord.Enable");
    }
    // NotifyTime.Discord.FirstRun
    public boolean getDiscordFirstRun() {
        return config.getBoolean("NotifyTime.Discord.FirstRun");
    }
    // NotifyTime.Discord.Seconds
    public List<Integer> getDiscordSeconds() {
        return config.getIntegerList("NotifyTime.Discord.Seconds");
    }
}