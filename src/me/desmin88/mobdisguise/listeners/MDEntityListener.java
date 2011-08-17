package me.desmin88.mobdisguise.listeners;

import me.desmin88.mobdisguise.DisguiseData;
import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.MobDisguiseData;
import me.desmin88.mobdisguise.utils.RealDropsUtils;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;


public class MDEntityListener extends EntityListener{
    @SuppressWarnings("unused")
    private final MobDisguise plugin;
    public MDEntityListener(final MobDisguise instance) {
        this.plugin = instance;
    }
    
    @Override
    public void onEntityTarget(final EntityTargetEvent event) {
        if(event.getTarget() instanceof Player) {
            final Player p = (Player) event.getTarget();
            final DisguiseData dd = MobDisguise.disMap.get(p);
            if(dd != null && dd instanceof MobDisguiseData && MobDisguise.cfg.getBoolean("MobTarget.enabled", true)) {
                event.setCancelled(true);
            }
        
        }
    }
    
    
    
    @Override
    public void onEntityDeath(final EntityDeathEvent event) {
        if(event.getEntity() instanceof Player) {
            final Player p = (Player) event.getEntity();
            final DisguiseData dd = MobDisguise.disMap.get(p);
            if(dd != null && dd instanceof MobDisguiseData && MobDisguise.cfg.getBoolean("RealDrops.enabled", false)) {
                event.getDrops().clear();
                if (RealDropsUtils.getDrop(p) != null) {
                    p.getWorld().dropItemNaturally(p.getLocation(), RealDropsUtils.getDrop(p));
                }
                
            }
        }
    }
}