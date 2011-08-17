package me.desmin88.mobdisguise.utils;

import me.desmin88.mobdisguise.MobDisguise;

import org.bukkit.entity.Player;

public enum MobType {
    Creeper (50, new CreeperAction()),
    Skeleton (51, new SkeletonAction()),
    Spider (52),
    Giant (53),
    Zombie (54),
    Slime (55),
    Pigman (57, new PigmanAction()),
    Pig (90),
    Sheep (91),
    Cow (92),
    Chicken (93),
    Squid (94),
    Wolf (95);
    
    private final byte id;
    private MobAction action;
    
    MobType (final int id, final MobAction... acts) {
        this.id = (byte) id;
        if (acts.length > 0) {
            this.action = acts[0];
        }
    }
    
    public byte getId() {
        return id;
    }
    
    public void doAction(final MobDisguise plugin, final Player player) {
        System.out.println("Doing action for " + name());
        if (this.action == null)
            return;
        this.action.onRightClick(plugin, player);
    }
}
