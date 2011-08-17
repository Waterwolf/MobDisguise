package me.desmin88.mobdisguise;

public class PlayerDisguiseData extends DisguiseData {
    
    private final String disguisingAs;
    
    public PlayerDisguiseData(final String disguisingAsName) {
        this.disguisingAs = disguisingAsName;
    }

    public String getDisguisingAs() {
        return disguisingAs;
    }

}
