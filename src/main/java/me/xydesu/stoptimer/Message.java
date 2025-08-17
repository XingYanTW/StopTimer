package me.xydesu.stoptimer;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Message {

    private FileConfiguration config;

    public Message(FileConfiguration config) {
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

    private String formatTime(long seconds) {
        long h = seconds / 3600;
        long m = (seconds % 3600) / 60;
        long s = seconds % 60;

        if (h > 0) return h + "小時 " + m + "分鐘 " + s + "秒";
        if (m > 0) return m + "分鐘 " + s + "秒";
        return s + "秒";
    }
}
