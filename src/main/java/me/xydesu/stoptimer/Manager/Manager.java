package me.xydesu.stoptimer.Manager;

import github.scarsz.discordsrv.DiscordSRV;
import me.xydesu.stoptimer.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Manager {

    private final Plugin plugin;
    private BukkitRunnable task;
    private long timeLeft = -1;
    private final MessageManager message;

    public Manager(Main plugin) {
        this.plugin = plugin;
        this.message = new MessageManager(plugin.getConfig());
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public long parseTime(String input) {
        try {
            char unit = input.charAt(input.length() - 1);
            long value = Long.parseLong(input.substring(0, input.length() - 1));

            switch (unit) {
                case 's': return value;
                case 'm': return value * 60;
                case 'h': return value * 3600;
                default: return -1;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    public void startCountdown(long seconds) {
        if (timeLeft > 0) {
            // Countdown already running
            return;
        }

        timeLeft = seconds;

        task = new BukkitRunnable() {
            boolean firstRun = true;

            @Override
            public void run() {
                // Send messages on first run and important intervals
                if (firstRun || timeLeft % 60 == 0 || timeLeft <= 10) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendTitle(message.getTitle(), message.getSubtitle(timeLeft), 10, 70, 20);
                        message.getMessage(timeLeft).forEach(player::sendMessage);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                    });
                    if(timeLeft <= 5 || firstRun || timeLeft % 60 == 0) {
                        try {
                            DiscordSRV.getPlugin().getMainTextChannel().sendMessage(message.getDiscordMessage(timeLeft)).queue();
                        } catch (Exception ex) {
                            plugin.getLogger().warning("無法發送 Discord 訊息：" + ex.getMessage());
                        }
                    }
                    firstRun = false;
                }

                // Countdown finished
                if (timeLeft <= 0) {
                    Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(message.getKickMessage()));
                    Bukkit.shutdown();
                    cancel();
                    return;
                }

                timeLeft--;
            }
        };

        task.runTaskTimer(plugin, 0, 20);
    }


    public boolean cancelCountdown() {
        if (timeLeft <= 0 || task == null) return false;
        task.cancel();
        timeLeft = -1;
        Bukkit.getOnlinePlayers().forEach(player -> {
            message.getNotifyCancel().forEach(player::sendMessage);
        });
        DiscordSRV.getPlugin().getMainTextChannel().sendMessage(message.getDiscordCancel()).queue();

        return true;
    }

}
