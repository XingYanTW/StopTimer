package me.xydesu.stoptimer.Manager;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;
import static me.xydesu.stoptimer.Utils.TimeUtil.formatTime;

public class MessageManager {

    private FileConfiguration config;
    private final Plugin plugin;
    private String language;

    public MessageManager(Plugin plugin, String language) {
        this.plugin = plugin;
        this.language = language;
        loadConfig();
    }

    public void reload(String language) {
        this.language = language;
        loadConfig();
    }

    private void loadConfig() {
        File file = new File(plugin.getDataFolder(), "Message." + language + ".yml");
        if (!file.exists()) {
            plugin.saveResource("Message." + language + ".yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }


    // Command messages
    public String getReload() {
        return color(config.getString("Messages.Command.Reload"));
    }

    public String getCommandUsage() {
        return color(config.getString("Messages.Command.Usage"));
    }

    public String getNoPermission() {
        return color(config.getString("Messages.Command.NoPermission"));
    }

    public String getErrorFormat() {
        return color(config.getString("Messages.Command.ErrorFormat"));
    }

    public String getCanceled() {
        return color(config.getString("Messages.Command.Canceled"));
    }

    public String getCancelFail() {
        return color(config.getString("Messages.Command.CancelFail"));
    }

    // Notify messages
    public String getTitle() {
        return color(config.getString("Messages.Notify.Title"));
    }

    public String getSubtitle(long time) {
        String t = formatTime(time);
        return color(config.getString("Messages.Notify.Subtitle").replace("%time%", t));
    }

    public String getPlaceholder(long time) {
        String t = formatTime(time);
        return color(config.getString("Messages.Placeholder.Message").replace("%time%", t));
    }

    public String getDiscordMessage(long time) {
        String t = formatTime(time);
        return color(config.getString("Messages.Discord.Message").replace("%time%", t));
    }

    public String getDiscordCancel() {
        return color(config.getString("Messages.Discord.Cancel"));
    }

    public String getBossbarMessage(long time) {
        String t = formatTime(time);
        return color(config.getString("Messages.Bossbar.Message").replace("%time%", t));
    }

    public List<String> getMessage(long time) {
        String t = formatTime(time);
        List<String> lines = config.getStringList("Messages.Notify.Message");
        return lines.stream()
                .map(line -> color(line.replace("%time%", t)))
                .toList();
    }

    public List<String> getNotifyCancel() {
        List<String> lines = config.getStringList("Messages.Notify.Cancel");
        return lines.stream()
                .map(this::color)
                .toList();
    }

    public String getKickMessage() {
        return color(config.getString("Messages.Notify.Kick"));
    }

    // Utility
    private String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}