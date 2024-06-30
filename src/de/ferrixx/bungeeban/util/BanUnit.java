package de.ferrixx.bungeeban.util;

import de.ferrixx.bungeeban.BungeeBan;

import java.util.ArrayList;
import java.util.List;


public enum BanUnit {

    SECOND(BungeeBan.bungeeBan.messagesConf.getString("Seconds:"), 1, "S"),
    MINUTE(BungeeBan.bungeeBan.messagesConf.getString("Minutes:"), 60, "M"),
    HOUR(BungeeBan.bungeeBan.messagesConf.getString("Hours:"), 60*60, "H"),
    DAY(BungeeBan.bungeeBan.messagesConf.getString("Days:"), 24*60*60, "D");

    private String name;
    private int toSecond;
    private String shortcut;

    private BanUnit(String name, int toSecond, String shortcut) {
        this.toSecond = toSecond;
        this.name = name;
        this.shortcut = shortcut;
    }

    public int getToSecond() {
        return toSecond;
    }

    public String getName() {
        return name;
    }

    public String getShortcut() {
        return shortcut;
    }

    public static List<String> getUnit() {
        List<String> units = new ArrayList<>();
        for(BanUnit unit : BanUnit.values()) {
            units.add(unit.getShortcut());
        }
        return units;
    }

    public static BanUnit getUnit(String unit) {
        for(BanUnit units : BanUnit.values()) {
            if(units.getShortcut().toLowerCase().equals(unit.toLowerCase())){
                return units;
            }
        }
        return null;
    }
}
