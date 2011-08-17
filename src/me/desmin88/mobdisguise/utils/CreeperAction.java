package me.desmin88.mobdisguise.utils;

import me.desmin88.mobdisguise.MobDisguise;

import org.bukkit.entity.Player;

public class CreeperAction implements MobAction {

    @Override
    public void onRightClick(final MobDisguise plugin, final Player player) {
        /*
        player.getWorld().createExplosion(player.getLocation(), 4);
        player.setHealth(0);
        MobDisguise.disMap.remove(player);
        */
    }

}
