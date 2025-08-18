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

    //NotifyTime.Title.FirstRun
    public String getTitleFirstRun() {
        return config.getString("NotifyTime.Title.FirstRun");
    }
    //NotifyTime.Title.Seconds
    public List<Integer> getTitleSeconds() {
        return config.getIntegerList("NotifyTime.Title.Seconds");
    }

    //NotifyTime.Message.FirstRun
    public String getMessageFirstRun() {
        return config.getString("NotifyTime.Message.FirstRun");
    }
    //NotifyTime.Message.Seconds
    public List<Integer> getMessageSeconds() {
        return config.getIntegerList("NotifyTime.Message.Seconds");
    }

    //NotifyTime.Title.FirstRun
    public String getDiscordFirstRun() {
        return config.getString("NotifyTime.Discord.FirstRun");
    }
    //NotifyTime.Title.Seconds
    public List<Integer> getDiscordSeconds() {
        return config.getIntegerList("NotifyTime.Discord.Seconds");
    }

}
