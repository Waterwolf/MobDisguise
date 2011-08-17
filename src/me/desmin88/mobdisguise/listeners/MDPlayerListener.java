package me.desmin88.mobdisguise.listeners;

import me.desmin88.mobdisguise.DisguiseData;
import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.MobDisguiseData;
import me.desmin88.mobdisguise.PlayerDisguiseData;
import me.desmin88.mobdisguise.api.MobDisguiseAPI;
import me.desmin88.mobdisguise.utils.DisguiseTask;

import org.bukkit.Bukkit;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MDPlayerListener extends PlayerListener {

    private final MobDisguise plugin;

    public MDPlayerListener(final MobDisguise instance) {
        this.plugin = instance;
    }

    @Override
    public void onPlayerPickupItem(final PlayerPickupItemEvent event) {
        final DisguiseData dd = MobDisguiseAPI.getDisguiseData(event
                .getPlayer());
        if (dd != null
                && !(dd instanceof PlayerDisguiseData)
                && MobDisguise.cfg
                        .getBoolean("DisableItemPickup.enabled", true)) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onPlayerQuit(final PlayerQuitEvent event) {
        if (MobDisguise.disMap.containsKey(event.getPlayer())) {
            // Should fix the "carcass" mob when disguised
            Bukkit.getServer().getScheduler()
                    .scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            MobDisguise.pUtils.killCarcass(event.getPlayer());
                        }
                    }, 5);
        }
    }

    // Waiting for my stinking pull.
    @Override
    public void onPlayerAnimation(final PlayerAnimationEvent event) {
        if (MobDisguise.disMap.containsKey(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }
    }

    @Override
    public void onPlayerBedEnter(final PlayerBedEnterEvent event) {
        if (MobDisguise.disMap.containsKey(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }
    }

    @Override
    public void onPlayerTeleport(final PlayerTeleportEvent event) {
        /*
        if (MobDisguise.telelist.contains(event.getPlayer().getName())) {
            MobDisguise.telelist.remove(event.getPlayer().getName());
            return;
        }TODO wat
        */
        if (!MobDisguise.disMap.containsKey(event.getPlayer())) {
            Bukkit.getServer()
                    .getScheduler()
                    .scheduleSyncDelayedTask(plugin, new DisguiseTask(plugin, event.getPlayer()),
                            8);
        }
        if (MobDisguise.disMap.containsKey(event.getPlayer())) {
            Bukkit.getServer()
                    .getScheduler()
                    .scheduleSyncDelayedTask(plugin, new DisguiseTask(plugin, event.getPlayer()),
                            8);
                event.getPlayer()
                        .sendMessage(
                                MobDisguise.pref
                                        + "You have been disguised because you teleported");
        }
    }

    @Override
    public void onPlayerInteract(final PlayerInteractEvent event) {

        final DisguiseData dd = MobDisguise.disMap.get(event.getPlayer());
        
        if (dd instanceof MobDisguiseData && event.getAction() == Action.LEFT_CLICK_AIR) {
            final MobDisguiseData mdd = (MobDisguiseData) dd;
            mdd.getMobType().doAction(plugin, event.getPlayer());
        }

    }

    @Override
    public void onPlayerRespawn(final PlayerRespawnEvent event) {

        if (!MobDisguise.disMap.containsKey(event.getPlayer())) {
            Bukkit.getServer()
                    .getScheduler()
                    .scheduleSyncDelayedTask(plugin, new DisguiseTask(plugin, event.getPlayer()),
                            8);
        }
        if (MobDisguise.disMap.containsKey(event.getPlayer().getName())) {
                event.getPlayer().sendMessage(
                        MobDisguise.pref
                                + "You have been disguised because you died");
            Bukkit.getServer()
                    .getScheduler()
                    .scheduleSyncDelayedTask(plugin, new DisguiseTask(plugin, event.getPlayer()),
                            8);
        }
    }

    @Override
    public void onPlayerJoin(final PlayerJoinEvent event) {
        if (MobDisguise.disMap.containsKey(event.getPlayer())) {
            Bukkit.getServer()
                    .getScheduler()
                    .scheduleSyncDelayedTask(plugin, new DisguiseTask(plugin, event.getPlayer()),
                            20);
                event.getPlayer()
                        .sendMessage(
                                MobDisguise.pref
                                        + "You have been disguised because you relogged");
        }
        /*
        if (!MobDisguise.disMap.containsKey(event.getPlayer())) {
            Bukkit.getServer()
                    .getScheduler()
                    .scheduleSyncDelayedTask(plugin, new DisguiseTask(plugin),
                            20);
        }
        */

    }
}
