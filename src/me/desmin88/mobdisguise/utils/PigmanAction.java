package me.desmin88.mobdisguise.utils;

import me.desmin88.mobdisguise.MobDisguise;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PigmanAction implements MobAction {

    @Override
    public void onRightClick(final MobDisguise plugin, final Player player) {
        final Block targ = player.getTargetBlock(null, 20);
        if (targ != null) {
            player.getWorld().strikeLightning(targ.getLocation());
        }
    }

}
