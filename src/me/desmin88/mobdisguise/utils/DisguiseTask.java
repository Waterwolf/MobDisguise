package me.desmin88.mobdisguise.utils;

import me.desmin88.mobdisguise.MobDisguise;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DisguiseTask implements Runnable {
    public MobDisguise plugin;
    private Player disguised;

    public DisguiseTask(final MobDisguise instance, final Player disguised) {
        plugin = instance;
    }

    @Override
    public void run() {
        MobDisguise.pUtils.disguiseToAll(disguised);
        /*
        for (final String s : MobDisguise.disList) {
            if(Bukkit.getServer().getPlayer(s) == null) {
                continue;
            }
            final Player temp = plugin.getServer().getPlayer(s);
            if(MobDisguise.playerdislist.contains(temp.getName())) {
                MobDisguise.pu.disguisep2pToAll(temp, MobDisguise.p2p.get(temp.getName()));
            }
            else {
                MobDisguise.pu.disguiseToAll(temp);
            }
        }
        */
    }
}
