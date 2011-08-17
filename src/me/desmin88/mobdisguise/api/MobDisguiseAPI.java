package me.desmin88.mobdisguise.api;

import me.desmin88.mobdisguise.DisguiseData;
import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.MobDisguiseData;
import me.desmin88.mobdisguise.PlayerDisguiseData;
import me.desmin88.mobdisguise.api.event.DisguiseAsMobEvent;
import me.desmin88.mobdisguise.api.event.DisguiseAsPlayerEvent;
import me.desmin88.mobdisguise.api.event.UnDisguiseEvent;
import me.desmin88.mobdisguise.utils.MobType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Basic API to disguise players.
 * 
 * @author desmin88
 * @author iffa
 *
 */
public class MobDisguiseAPI {
	/**
	 * Disguises a player as a player.
	 * 
	 * @param p Player to disguise
	 * @param name Player name to disguise as
	 * 
	 * @return true if succesful
	 */
	public static boolean disguisePlayerAsPlayer(final Player p, String name) {
		if (isDisguised(p))
            return false;
		if (name.length() > 16) {
			System.out.println(MobDisguise.pref + "Error, some other plugin is setting a name over 16 characters, truncating.");
			final String tmp = name.substring(0, 16);
			name = tmp;
		}
		/* Listener notify start */
		final DisguiseAsPlayerEvent e = new DisguiseAsPlayerEvent("DisguiseAsPlayerEvent", p, name);
		Bukkit.getServer().getPluginManager().callEvent(e);
		if(e.isCancelled())
            return false;
		
		MobDisguise.disMap.put(p, new PlayerDisguiseData(name));
		MobDisguise.pUtils.disguiseToAll(p);

		return true;
	}
	
	public static boolean undisguisePlayer(final Player p) {
        if (!isDisguised(p))
            return true;
        
        final UnDisguiseEvent e = new UnDisguiseEvent("UnDisguiseEvent", p, false);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if(e.isCancelled())
            return false;
        
        MobDisguise.pUtils.undisguiseToAll(p);
        MobDisguise.disMap.remove(p);
        
        return true;
	}
	
	public static DisguiseData getDisguiseData(final Player p) {
	    return MobDisguise.disMap.get(p);
	}

	/**
	 * Disguises a player as a mob.
	 * 
	 * @param p Player to disguise
	 * @param mobtype Mob to disguise as
	 * 
	 * @return true if successful
	 */
	public static boolean disguisePlayer(final Player p, final MobType mobType) {
		if (isDisguised(p))
            return false;
		
		final DisguiseAsMobEvent e = new DisguiseAsMobEvent("DisguiseAsMobEvent", p, mobType);
		Bukkit.getServer().getPluginManager().callEvent(e);
		if(e.isCancelled())
            return false;
		
		MobDisguise.disMap.put(p, new MobDisguiseData(mobType));
		MobDisguise.pUtils.disguiseToAll(p);
		return true;
	}

	/**
	 * Undisguises a player who is disguised as a mob.
	 * 
	 * @param p Player to undisguise
	 * @param mobtype ???
	 * 
	 * @return true if successful
	 */
	/*
	public static boolean undisguisePlayer(final Player p, final String mobtype) {
		if (isDisguised(p))
            return false;
		/* Listener notify start *
		final UnDisguiseEvent e = new UnDisguiseEvent("UnDisguiseEvent", p, true);
		Bukkit.getServer().getPluginManager().callEvent(e);
		if(e.isCancelled())
            return false;
		/* Listener notify end *
		MobDisguise.pu.undisguiseToAll(p);
		MobDisguise.disList.remove(p);
		MobDisguise.apiList.remove(p.getName());
		MobDisguise.playerMobId.put(p.getName(), null);
		MobDisguise.playerEntIds.remove(Integer.valueOf(p.getEntityId()));
		return true;

	}*/

	/**
	 * Checks if a player is disguised.
	 * 
	 * @param p Player
	 * 
	 * @return true if disguised
	 */
	public static boolean isDisguised(final Player p) {
		return MobDisguise.disMap.containsKey(p);
	}

}
