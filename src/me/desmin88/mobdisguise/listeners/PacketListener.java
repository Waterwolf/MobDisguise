package me.desmin88.mobdisguise.listeners;

import me.desmin88.mobdisguise.DisguiseData;
import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.PlayerDisguiseData;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet17;
import net.minecraft.server.Packet18ArmAnimation;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.packet.listener.Listener;

public class PacketListener implements Listener {
    @SuppressWarnings("unused")
    private final MobDisguise plugin;

    public PacketListener(final MobDisguise instance) {
        this.plugin = instance;
    }

    @Override
    public boolean checkPacket(final Player p, final Packet packet) {
        if (packet instanceof Packet18ArmAnimation) {
            final Packet18ArmAnimation p18 = (Packet18ArmAnimation) packet;
            if (p18.b != 2 && entIdExists(p18.a))
                return false;
        }
        if (packet instanceof Packet17) {
            final Packet17 p17 = (Packet17) packet;
            if (entIdExists(p17.a))
                return false;
        }
        return true;
    }
    
    public static boolean entIdExists(final int id) {
        for (final Player p : MobDisguise.disMap.keySet()) {
            if (p.getEntityId() == id)
                return true;
        }
        return false;
    }

}
