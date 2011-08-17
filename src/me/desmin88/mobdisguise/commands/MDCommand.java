package me.desmin88.mobdisguise.commands;

import java.util.Arrays;

import me.desmin88.mobdisguise.DisguiseData;
import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.MobDisguiseData;
import me.desmin88.mobdisguise.PlayerDisguiseData;
import me.desmin88.mobdisguise.api.MobDisguiseAPI;
import me.desmin88.mobdisguise.api.event.DisguiseCommandEvent;
import me.desmin88.mobdisguise.api.event.UnDisguiseEvent;
import me.desmin88.mobdisguise.utils.MobType;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author desmin88
 * @author iffa
 *
 */
public class MDCommand implements CommandExecutor {
    @SuppressWarnings("unused")
    private final MobDisguise plugin;

    public MDCommand(final MobDisguise instance) {
        this.plugin = instance;
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String commandLabel, final String[] args) {
    	/* Listener notify start */
		final DisguiseCommandEvent ev = new DisguiseCommandEvent(
				"DisguiseCommandEvent", sender, args);
		Bukkit.getServer().getPluginManager().callEvent(ev);
		if(ev.isCancelled())
            return true;
		/* Listener notify end */
        if (sender instanceof Player) {
            final Player s = (Player) sender;
            final DisguiseData dd = MobDisguise.disMap.get(s);
            if (args.length == 0) { // Undisguising, player types /md
                if(dd == null) {
                    s.sendMessage(MobDisguise.pref + "You are not disguised, so you can't undisguise!");
                    return true;
                }
                if(dd instanceof PlayerDisguiseData) {
            		/* Listener notify start */
            		final UnDisguiseEvent e = new UnDisguiseEvent(
            				"UnDisguiseEvent", s, false);
            		Bukkit.getServer().getPluginManager().callEvent(e);
            		if(e.isCancelled())
                        return false;
            		/* Listener notify end */
            		
            		MobDisguise.pUtils.undisguiseToAll(s);
            		MobDisguise.disMap.remove(s);
                    
                    s.sendMessage(MobDisguise.pref + "You have undisguised as a different player and returned back to normal!");
                    return true;
                }
                
        		/* Listener notify start */
        		final UnDisguiseEvent e = new UnDisguiseEvent(
        				"UnDisguiseEvent", s, true);
        		Bukkit.getServer().getPluginManager().callEvent(e);
        		if(e.isCancelled())
                    return false;
        		/* Listener notify end */

                MobDisguise.pUtils.undisguiseToAll(s);
                MobDisguise.disMap.remove(s);
        		
                s.sendMessage(MobDisguise.pref + "You have been changed back to human form!");
                return true;
            }
            
            if (args[0].equalsIgnoreCase("types")) { // They want to know valid types of mobs
                if(s.isOp() || s.hasPermission("mobdisguise.*")) {
                    s.sendMessage(MobDisguise.pref + Arrays.asList(MobType.values()));
                    return true;
                }
                String available = new String("");
                
                for(final MobType key : MobType.values()) {
                    
                    if(s.hasPermission("mobdisguise." + key)) {
                        available = available + key.name() + ", ";
                    }
                }
                s.sendMessage(MobDisguise.pref + available);
                return true;
            }
            if (args[0].equalsIgnoreCase("stats")) { // They want to know they're current disguing status
                
                if(dd == null){
                    s.sendMessage(MobDisguise.pref + "You are currently NOT disguised!");
                    return true;
                }
                else if(dd instanceof PlayerDisguiseData) {
                    s.sendMessage(MobDisguise.pref + "You are currently disguised as player " + ((PlayerDisguiseData)dd).getDisguisingAs());
                    return true;
                }
                else if (dd instanceof MobDisguiseData){
                    s.sendMessage(MobDisguise.pref + "You are currently disguised as a " + ((MobDisguiseData)dd).getMobType().name());
                    return true;
                }
                
            }
             
            if(args[0].equalsIgnoreCase("p") && args.length == 2) {
                if(MobDisguise.perm && !s.isOp()){
                    if(!s.hasPermission("mobdisguise.player") ) {
                        s.sendMessage(MobDisguise.pref + "You don't have permission to change into other players!");
                        return true;
                    }
                }
                if(!MobDisguise.perm && !s.isOp()) {
                    s.sendMessage(MobDisguise.pref + "You are not an OP and perms are disabled!");
                    return true;
                }
                if(args[1].length() > 16) {
                    s.sendMessage(MobDisguise.pref + "That username is too long!");
                    return true;
                }

				MobDisguiseAPI.disguisePlayerAsPlayer(s, args[1]);
				
                s.sendMessage(MobDisguise.pref + "You have disguised as player " + args[1]);
                return true;
            }
            
            if(args.length == 1) { // Means they're trying to disguise
                final String mobtypes = args[0].toLowerCase();
                
                MobType type = null;
                
                for (final MobType tt : MobType.values()) {
                    if (tt.name().toLowerCase().equals(mobtypes)) {
                        type = tt;
                        break;
                    }
                }
                 
                if (type == null) {
                    s.sendMessage(MobDisguise.pref + "Invalid mob type!");
                    return true;
                }
                if(MobDisguise.perm && !s.isOp()){
                    if(!s.hasPermission("mobdisguise." + type.name()) ) {
                        s.sendMessage(MobDisguise.pref + "You don't have permission for this mob!");
                        return true;
                    }
                }
                if(!MobDisguise.perm && !s.isOp()) {
                    s.sendMessage(MobDisguise.pref + "You are not an OP and perms are disabled!");
                    return true;
                }
                if (!MobDisguise.cfg.getBoolean("BlackList." + type.name(), true)) {
                    s.sendMessage(MobDisguise.pref + "This mob type has been restricted!");
                    return true;
                }

                if (!MobDisguiseAPI.disguisePlayer(s, type)) {
                    s.sendMessage("unable to disguise");
                }
                else {
                    s.sendMessage(MobDisguise.pref + "You have been disguised as a " + type.name() + "!");
                }
                return true;
            
            }

        }
        return false;
    }
}
