package me.desmin88.mobdisguise.api.event;

import me.desmin88.mobdisguise.utils.MobType;

import org.bukkit.entity.Player;

/**
 * Event data for when a player disguises as a mob.
 * @author iffa
 *
 */
public class DisguiseAsMobEvent extends DisguiseEvent {
	private static final long serialVersionUID = 1706630423687514665L;
	private final MobType mobtype;

	public DisguiseAsMobEvent(final String event, final Player player, final MobType mobtype) {
		super(event, player);
		this.mobtype = mobtype;
	}
	
	/**
	 * Gets the mobtype the player is disguising as.
	 * 
	 * @return Mobtype
	 */
	public MobType getMobType() {
		return this.mobtype;
	}

}
