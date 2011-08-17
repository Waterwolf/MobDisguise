package me.desmin88.mobdisguise;

import me.desmin88.mobdisguise.utils.MobType;
import net.minecraft.server.DataWatcher;

public class MobDisguiseData extends DisguiseData {
    
    private final MobType type;
    private DataWatcher dataWatcher = null;
    
    public MobDisguiseData(final MobType mobType) {
        this.type = mobType;
    }

    public MobType getMobType() {
        return type;
    }
    
    public byte getType() {
        return this.type.getId();
    }

    public void setDataWatcher(final DataWatcher dataWatcher) {
        this.dataWatcher = dataWatcher;
    }

    public DataWatcher getDataWatcher() {
        return dataWatcher;
    }
}
