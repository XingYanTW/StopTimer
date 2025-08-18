package me.xydesu.stoptimer.Manager;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

import static me.xydesu.stoptimer.Utils.TimeUtil.formatTime;

public class MessageManager {

    private FileConfiguration config;

    public MessageManager(FileConfiguration config) {
        this.config = config;
    }

    public void reload(FileConfiguration newConfig) {
        this.config = newConfig;
    }

    public String getReload(){
        return color(config.getString("messages.command.reload"));
    }

    // Command messages
    public String getCommandUsage() {
        return color(config.getString("messages.command.usage"));
    }

    public String getNoPermission() {
        return color(config.getString("messages.command.nopermission"));
    }

    public String getErrorFormat() {
        return color(config.getString("messages.command.errorformat"));
    }

    public String getCanceled() {
        return color(config.getString("messages.command.canceled"));
    }

    public String getCancelFail() {
        return color(config.getString("messages.command.cancelfail"));
    }

    // Notify messages
    public String getTitle() {
        return color(config.getString("messages.notify.title"));
    }

    public String getSubtitle(long time) {
        String t = formatTime(time);
        return color(config.getString("messages.notify.subtitle").replace("%time%", t));
    }


    public String getPlaceholder(long time) {
        String t = formatTime(time);
        return color(config.getString("messages.placeholder.message").replace("%time%", t));
    }

    public String getDiscordMessage(long time) {
        String t = formatTime(time);
        return color(config.getString("messages.discord.message").replace("%time%", t));
    }

    public String getDiscordCancel() {
        return color(config.getString("messages.discord.cancel"));
    }

    public String getBossbarMessage(long time){
        String t = formatTime(time);
        return color(config.getString("messages.bossbar.message").replace("%time%", t));
    }



    public List<String> getMessage(long time) {
        String t = formatTime(time);
        List<String> lines = config.getStringList("messages.notify.message");
        return lines.stream()
                .map(line -> color(line.replace("%time%", t)))
                .toList();
    }

    public List<String> getNotifyCancel() {
        List<String> lines = config.getStringList("messages.notify.cancel");
        return lines.stream()
                .map(this::color)
                .toList();
    }

    public String getKickMessage() {
        return color(config.getString("messages.notify.kick"));
    }

    // Utility
    private String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
