package me.hexa.framedupe;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class FrameDupe extends JavaPlugin {

    Logger LOGGER = getLogger();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        LOGGER.info(ChatColor.translateAlternateColorCodes('&', "&9Frame Dupe plugin enabled"));
        getServer().getPluginManager().registerEvents(new FrameDupeListener(), this);
        getCommand("framedupereload").setExecutor(new ReloadCommand());
    }

    @Override
    public void onDisable() {
        LOGGER.info(ChatColor.translateAlternateColorCodes('&', "&9Frame Dupe plugin disabled"));
    }

    private final class FrameDupeListener implements Listener {

        @EventHandler
        private void onFrameBreak(EntityDamageByEntityEvent event) {
            if (event.getEntityType() == EntityType.ITEM_FRAME) {
                int rng = (int)Math.round(Math.random() * 100);
                if (rng < getConfig().getInt("probability-percentage")) {
                    event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), ((ItemFrame) event.getEntity()).getItem());
                }
            }
        }

    }

    private final class ReloadCommand implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender.isOp()) {
                reloadConfig();
                return true;
            }
            return false;
        }

    }

}
