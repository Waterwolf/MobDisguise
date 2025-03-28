package me.desmin88.mobdisguise;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import me.desmin88.mobdisguise.commands.MDCommand;
import me.desmin88.mobdisguise.listeners.MDEntityListener;
import me.desmin88.mobdisguise.listeners.MDPlayerListener;
import me.desmin88.mobdisguise.utils.MobType;
import me.desmin88.mobdisguise.utils.PacketUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
 
public class MobDisguise extends JavaPlugin {
    /*
    public static Set<String> disList = new HashSet<String>();
    public static Set<String> apiList = new HashSet<String>();
    public static Map<String, Byte> playerMobId = new HashMap<String, Byte>();
    //Player -> Datawatcher
    public static Map<String, DataWatcher> data = new HashMap<String, DataWatcher>();
    
    //Player disguising -> player disguised as
    public static Map<String, String> p2p = new HashMap<String, String>();
    public static Set<String> playerdislist = new HashSet<String>();
    //end
    public static Set<Integer> playerEntIds = new HashSet<Integer>();
    public static PacketUtils pu = new PacketUtils();
    public static Set<String> telelist = new HashSet<String>();
    */
    public static final Map<Player, DisguiseData> disMap = new HashMap<Player, DisguiseData>();
    public static final PacketUtils pUtils = new PacketUtils();
    
    //public final PacketListener packetlistener = new PacketListener(this);
    public final MDPlayerListener playerlistener = new MDPlayerListener(this);
    public final MDEntityListener entitylistener = new MDEntityListener(this);
    public static final String pref = ChatColor.DARK_PURPLE + "[MobDisguise] " + ChatColor.LIGHT_PURPLE;
    public static Configuration cfg;
    public static boolean perm;
    public static PluginDescriptionFile pdf;
    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
        System.out.println("[" + pdf.getName()+ "]" + " by " + Arrays.toString(pdf.getAuthors().toArray(new String[0])) + " version " + pdf.getVersion() + " disabled.");
    
    }

    @Override
    public void onEnable() {
        pdf=this.getDescription();
        // Begin config code
        if (!new File(getDataFolder(), "config.yml").exists()) {
            try {
                getDataFolder().mkdir();
                new File(getDataFolder(),"config.yml").createNewFile();
            }
            catch(final Exception e) {
                e.printStackTrace();
                System.out.println(pref + "Error making config.yml?!");
                getServer().getPluginManager().disablePlugin(this); //Cleanup
                return;
            }
        }
        cfg = this.getConfiguration(); // Get config

        
       if (cfg.getKeys().isEmpty()) { // Config hasn't been made
            System.out.println(pref + "config.yml not found, making with default values");
            cfg.setProperty("RealDrops.enabled", false);
            cfg.setProperty("Permissions.enabled", true);
            cfg.setProperty("MobTarget.enabled", true);
            cfg.setProperty("DisableItemPickup", true);
            for (final MobType mobtype : MobType.values()) {
                cfg.setHeader("#Setting a mobtype to false will not allow a player to disguise as that type");
                cfg.setProperty("Blacklist." + mobtype, true); // Just making
            }
            cfg.save();
        }
       if(cfg.getProperty("MobTarget.enabled") == null || cfg.getProperty("DisableItemPickup.enabled") == null) {
           cfg.setProperty("MobTarget.enabled", true);
           cfg.setProperty("DisableItemPickup.enabled", true);
           cfg.save();
       }
       cfg.save();
       perm = cfg.getBoolean("Permissions.enabled", true);
        
        final PluginManager pm = getServer().getPluginManager();
        this.getCommand("md").setExecutor(new MDCommand(this));
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerlistener, Priority.Lowest, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerlistener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerlistener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_TELEPORT, playerlistener, Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLAYER_PICKUP_ITEM, playerlistener, Priority.Monitor, this);
        pm.registerEvent(Event.Type.ENTITY_DEATH, entitylistener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_TARGET, entitylistener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_ANIMATION, playerlistener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerlistener, Priority.Normal, this);
        
        // new MDPlayerListener(this), Priority.Normal, this);
        //getServer().getScheduler().scheduleSyncRepeatingTask(this, new DisguiseTask(this), 1200, 1200);
        // Register packet listeners
        //org.getspout.spoutapi.packet.listener.Listeners.addListener(17, packetlistener);
        //org.getspout.spoutapi.packet.listener.Listeners.addListener(18, packetlistener);
        System.out.println("[" + pdf.getName() + "]"  + " by " + Arrays.toString(pdf.getAuthors().toArray(new String[0])) + " version " + pdf.getVersion() + " enabled.");

    }

}