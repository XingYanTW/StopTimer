package me.xydesu.stoptimer.commands;

import me.xydesu.stoptimer.Manager;
import me.xydesu.stoptimer.Message;
import me.xydesu.stoptimer.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class stopserver implements CommandExecutor, TabCompleter {

    private final Manager manager;
    private final Message messages;

    public stopserver(Manager manager) {
        this.manager = manager;
        this.messages = new Message(main.getInstance().getConfig());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Permission check
        if (!(sender.hasPermission("stoptimer.stopserver") || sender.isOp())) {
            sender.sendMessage(messages.getNoPermission());
            return true;
        }

        // Reload config
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            main.getInstance().reloadConfig();
            messages.reload(main.getInstance().getConfig());
            sender.sendMessage(messages.getReload());
            return true;
        }

        // Cancel countdown
        if (args.length == 1 && args[0].equalsIgnoreCase("cancel")) {
            if (manager.cancelCountdown()) {
                sender.sendMessage(messages.getCanceled());
            } else {
                sender.sendMessage(messages.getCancelFail());
            }
            return true;
        }

        // Require exactly 1 argument (time)
        if (args.length != 1) {
            sender.sendMessage(messages.getCommandUsage());
            return true;
        }

        // Parse time
        long seconds = manager.parseTime(args[0]);
        if (seconds <= 0) {
            sender.sendMessage(messages.getErrorFormat());
            return true;
        }

        // Start countdown
        manager.startCountdown(seconds);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        // Inside onTabComplete
        if (args.length == 1) {
            List<String> suggestions = new ArrayList<>();
            String input = args[0].toLowerCase();

            // Add reload
            if ("reload".startsWith(input) && (sender.hasPermission("stoptimer.stopserver") || sender.isOp())) {
                suggestions.add("reload");
            }

            // Add cancel if countdown running
            if (manager.getTimeLeft() > 0 && "cancel".startsWith(input)) {
                suggestions.add("cancel");
            }

            // Digit suggestions
            if (input.matches("\\d+")) {
                String[] units = {"s", "m", "h", "d", "w", "y"};
                for (String unit : units) {
                    suggestions.add(input + unit);
                }
                return suggestions;
            }

            // Examples
            List<String> examples = Arrays.asList("30s", "1m", "5m", "10m", "1h");
            for (String ex : examples) {
                if (ex.startsWith(input)) suggestions.add(ex);
            }

            return suggestions;
        }
        return new ArrayList<>();
    }
}
