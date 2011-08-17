package me.desmin88.mobdisguise.utils;

import java.lang.reflect.Field;

import me.desmin88.mobdisguise.DisguiseData;
import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.MobDisguiseData;
import me.desmin88.mobdisguise.PlayerDisguiseData;
import me.desmin88.mobdisguise.api.MobDisguiseAPI;
import net.minecraft.server.DataWatcher;
import net.minecraft.server.MathHelper;
import net.minecraft.server.Packet20NamedEntitySpawn;
import net.minecraft.server.Packet24MobSpawn;
import net.minecraft.server.Packet29DestroyEntity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketUtils {
    
    public PacketUtils() {}

    
    public void killCarcass(final Player p1) {
        //Make packets out of loop!
        final CraftPlayer p22 = (CraftPlayer) p1;
        final Packet29DestroyEntity p29 = new Packet29DestroyEntity(p22.getEntityId());
        for (final Player p2 : Bukkit.getServer().getOnlinePlayers()) {
            if (p2.getName().equals(p1.getName())) {
                continue;
            }
            ((CraftPlayer) p2).getHandle().netServerHandler.sendPacket(p29);
            ((CraftPlayer) p2).getHandle().netServerHandler.sendPacket(p29);
            
        }
    }
    /*
    public void undisguiseToAll(final Player p1) {
        //Make packets out of loop!
        final CraftPlayer p22 = (CraftPlayer) p1;
        final Packet29DestroyEntity p29 = new Packet29DestroyEntity(p22.getEntityId());
        final Packet20NamedEntitySpawn p20 = new Packet20NamedEntitySpawn(p22.getHandle());
        
        for (final Player p2 : Bukkit.getServer().getOnlinePlayers()) {
            if (p2 == p1) {
                continue;
            }
            ((CraftPlayer) p2).getHandle().netServerHandler.sendPacket(p29);
            ((CraftPlayer) p2).getHandle().netServerHandler.sendPacket(p20);
        }
    }
    
    public void disguiseToAll(final Player p1) {
        //Make packets out of loop!
        final Packet24MobSpawn p24 = makePlayerSpawnPacket(p1, MobDisguise.playerMobId.get(p1.getName()));
        for (final Player p2 : Bukkit.getServer().getOnlinePlayers()) {
            if (p2 == p1) {
                continue;
            }
            ((CraftPlayer) p2).getHandle().netServerHandler.sendPacket(p24);
        }
    }

    //Begin code for p2p disguising
    public void disguisep2pToAll(final Player p, final String name) {
        final Packet20NamedEntitySpawn p20 = makePlayerSpawnPacket(p, name);
        final Packet29DestroyEntity p29 = new Packet29DestroyEntity(p.getEntityId()); //Must destroy, don't want doubles lololololol
        p.setDisplayName(name);
        for (final Player p1 : Bukkit.getServer().getOnlinePlayers()) {
            if (p1 == p) {
                continue;
            }
            ((CraftPlayer) p1).getHandle().netServerHandler.sendPacket(p29);
            ((CraftPlayer) p1).getHandle().netServerHandler.sendPacket(p20);
        }
    }
    
    public void undisguisep2pToAll(final Player p) {
        p.setDisplayName(p.getName());
        final Packet20NamedEntitySpawn p20 = makePlayerSpawnPacket(p, p.getName());
        final Packet29DestroyEntity p29 = new Packet29DestroyEntity(p.getEntityId()); //Must destroy, don't want doubles lololololol
        for (final Player p1 : Bukkit.getServer().getOnlinePlayers()) {
            if (p1 == p) {
                continue;
            }
            ((CraftPlayer) p1).getHandle().netServerHandler.sendPacket(p29);
            ((CraftPlayer) p1).getHandle().netServerHandler.sendPacket(p20);
        }
    }*/
    
    public void disguiseToAll(final Player p) {
        final DisguiseData dd = MobDisguiseAPI.getDisguiseData(p);
        if (dd instanceof PlayerDisguiseData) {
            final PlayerDisguiseData pdd = (PlayerDisguiseData) dd;
            final Packet20NamedEntitySpawn p20 = makePlayerSpawnPacket(p, pdd.getDisguisingAs());
            final Packet29DestroyEntity p29 = new Packet29DestroyEntity(p.getEntityId()); //Must destroy, don't want doubles lololololol
            p.setDisplayName(pdd.getDisguisingAs());
            for (final Player p1 : Bukkit.getServer().getOnlinePlayers()) {
                if (p1 == p) {
                    continue;
                }
                ((CraftPlayer) p1).getHandle().netServerHandler.sendPacket(p29);
                ((CraftPlayer) p1).getHandle().netServerHandler.sendPacket(p20);
            }
        }
        else if (dd instanceof MobDisguiseData) {
            final MobDisguiseData mdd = (MobDisguiseData) dd;
            final Packet24MobSpawn p24 = makeMobSpawnPacket(p, mdd);
            for (final Player p2 : Bukkit.getServer().getOnlinePlayers()) {
                if (p2 == p) {
                    continue;
                }
                ((CraftPlayer) p2).getHandle().netServerHandler.sendPacket(p24);
            }
        }
        else if (dd != null) {
            System.out.println("DD is neither?" + dd);
        }
    }
    public void undisguiseToAll(final Player p) {
        final DisguiseData dd = MobDisguiseAPI.getDisguiseData(p);
        if (dd == null)
            return;
        if (dd instanceof PlayerDisguiseData) {
            p.setDisplayName(p.getName());
            final Packet20NamedEntitySpawn p20 = makePlayerSpawnPacket(p, p.getName());
            final Packet29DestroyEntity p29 = new Packet29DestroyEntity(p.getEntityId()); //Must destroy, don't want doubles lololololol
            for (final Player p1 : Bukkit.getServer().getOnlinePlayers()) {
                if (p1 == p) {
                    continue;
                }
                ((CraftPlayer) p1).getHandle().netServerHandler.sendPacket(p29);
                ((CraftPlayer) p1).getHandle().netServerHandler.sendPacket(p20);
            }
        }
        else if (dd instanceof MobDisguiseData) {
            //Make packets out of loop!
            final CraftPlayer p22 = (CraftPlayer) p;
            final Packet29DestroyEntity p29 = new Packet29DestroyEntity(p22.getEntityId());
            final Packet20NamedEntitySpawn p20 = new Packet20NamedEntitySpawn(p22.getHandle());
            
            for (final Player p2 : Bukkit.getServer().getOnlinePlayers()) {
                if (p2 == p) {
                    continue;
                }
                ((CraftPlayer) p2).getHandle().netServerHandler.sendPacket(p29);
                ((CraftPlayer) p2).getHandle().netServerHandler.sendPacket(p20);
            }
        }
    }
    
    public static Packet20NamedEntitySpawn makePlayerSpawnPacket(final Player p, final String disguiseName) {
        final Location loc = p.getLocation();
        final Packet20NamedEntitySpawn packet = new Packet20NamedEntitySpawn();
        packet.a = p.getEntityId();
        packet.b = disguiseName; //Set the name of the player to the name they want.
        packet.c = (int) loc.getX();
        packet.c = MathHelper.floor(loc.getX() * 32.0D);
        packet.d = MathHelper.floor(loc.getY() * 32.0D);
        packet.e = MathHelper.floor(loc.getZ() * 32.0D);
        packet.f = (byte) ((int) loc.getYaw() * 256.0F / 360.0F);
        packet.g = (byte) ((int) (loc.getPitch() * 256.0F / 360.0F));
        packet.h = p.getItemInHand().getTypeId();
        return packet;
        
    }
    
    
    public Packet24MobSpawn makeMobSpawnPacket(final Player p1, final MobDisguiseData mdd) {
        
        final byte id = mdd.getType();
        
        final DataWatcher tmp = new DataWatcher();
        mdd.setDataWatcher(tmp);
        if(id == 55) {
            tmp.a(16, (byte) 1);
        }
        if(id == 91) {
            tmp.a(16, (byte) 3);
        }
        final Location loc = p1.getLocation();
        final Packet24MobSpawn packet = new Packet24MobSpawn();
        packet.a = ((CraftPlayer) p1).getEntityId();
        packet.b = id;
        packet.c = MathHelper.floor(loc.getX() * 32.0D);
        packet.d = MathHelper.floor(loc.getY() * 32.0D);
        packet.e = MathHelper.floor(loc.getZ() * 32.0D);
        packet.f = (byte) ((int) loc.getYaw() * 256.0F / 360.0F);
        packet.g = (byte) ((int) (loc.getPitch() * 256.0F / 360.0F));
        Field datawatcher;
        try {
            datawatcher = packet.getClass().getDeclaredField("h");
            datawatcher.setAccessible(true);
            datawatcher.set(packet, tmp);
            datawatcher.setAccessible(false);
        } catch (final Exception e) {
            System.out.println(MobDisguise.pref + "Error making packet?!");
            return null;
        }
        return packet;
    }

}
