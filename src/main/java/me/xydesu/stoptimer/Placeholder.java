package me.xydesu.stoptimer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Placeholder extends PlaceholderExpansion {

    private final Plugin plugin;
    private final Manager manager;
    private final Message message;

    public Placeholder(Plugin plugin, Manager manager) {
        this.plugin = plugin;
        this.manager = manager;
        this.message = new Message(plugin.getConfig());
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
            return (timeLeft > 0) ? manager.formatTime(timeLeft+1) : "";
        }

        if (identifier.equalsIgnoreCase("message")) {
            return (timeLeft > 0)
                    ? message.getPlaceholder(timeLeft+1)
                    : "";
        }

        return null;
    }

}
