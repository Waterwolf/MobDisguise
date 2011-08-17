package me.desmin88.mobdisguise.utils;

import me.desmin88.mobdisguise.MobDisguise;

import org.bukkit.entity.Player;

public class SkeletonAction implements MobAction {

    @Override
    public void onRightClick(final MobDisguise plugin, final Player player) {
        player.shootArrow().setBounce(false);
    }

}
