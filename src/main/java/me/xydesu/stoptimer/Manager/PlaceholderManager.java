package me.xydesu.stoptimer.Manager;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import static me.xydesu.stoptimer.Utils.TimeUtil.formatTime;

public class PlaceholderManager extends PlaceholderExpansion {

    private final Plugin plugin;
    private final Manager manager;
    private final MessageManager message;

    public PlaceholderManager(Plugin plugin, Manager manager) {
        this.plugin = plugin;
        this.manager = manager;
        this.message = new MessageManager(plugin.getConfig());
    }

    @Override
    public @NotNull String getIdentifier() {
        return "stoptimer";
    }

    @Override
    public @NotNull String getAuthor() {
        return "xydesu";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        long timeLeft = manager.getTimeLeft();

        if (identifier.equalsIgnoreCase("time")) {
            return (timeLeft > 0) ? formatTime(timeLeft+1) : "";
        }

        if (identifier.equalsIgnoreCase("message")) {
            return (timeLeft > 0)
                    ? message.getPlaceholder(timeLeft+1)
                    : "";
        }
        return null;
    }
}
