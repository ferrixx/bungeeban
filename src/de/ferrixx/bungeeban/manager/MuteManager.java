package de.ferrixx.bungeeban.manager;

import de.ferrixx.bungeeban.BungeeBan;
import de.ferrixx.bungeeban.mysql.MySQL;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MuteManager {

    public static void mute(String uuid, String playername, String reason, long seconds, String from, String date) {
        long end = 0;
        if(seconds == -1) {
            end = -1;
        } else {
            long current = System.currentTimeMillis();
            long millis = seconds*1000;
            end = current+millis;
        }
        MySQL.update("INSERT INTO mutes (name, uuid, end, reason, mutedBy, date) VALUES ('"+playername+"', '"+uuid+"','"+end+"','"+reason+"','"+from+"','"+date+"')");
        if(BungeeCord.getInstance().getPlayer(playername) != null) {
            ProxiedPlayer p = BungeeCord.getInstance().getPlayer(playername);
            String mutedReason =
                    BungeeBan.bungeeBan.messagesConf.getString("MutedMessage:").
                            replaceAll("%reason%", getReason(uuid)).
                            replaceAll("%reamingtime%", getRemainingTime(uuid));
            p.sendMessage(new TextComponent(mutedReason));
        }
    }

    public static void unmute(String uuid) {
        MySQL.update("DELETE FROM mutes WHERE UUID='"+uuid+"'");
    }

    public static boolean isMuted(String uuid) {
        ResultSet rs = MySQL.getResultSet("SELECT * FROM mutes WHERE UUID='"+uuid+"'");
        try {
            return rs.next();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getReason(String uuid) {
        ResultSet rs = MySQL.getResultSet("SELECT * FROM mutes WHERE UUID='"+uuid+"'");
        try {
            while(rs.next()) {
                return rs.getString("reason");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Long getEnd(String uuid) {
        ResultSet rs = MySQL.getResultSet("SELECT * FROM mutes WHERE UUID='"+uuid+"'");
        try {
            while(rs.next()) {
                return rs.getLong("end");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getMutedPlayes() {
        List<String> list = new ArrayList<>();
        ResultSet rs = MySQL.getResultSet("SELECT * FROM mutes");
        try {
            while(rs.next()) {
                list.add(rs.getString("name"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String getRemainingTime(String uuid) {
        long current = System.currentTimeMillis();
        long end = getEnd(uuid);
        if(end == -1) {
            return BungeeBan.bungeeBan.messagesConf.getString("PermanedBanned:");
        }
        long millis = end - current;

        long seconds = 0;
        long minutes = 0;
        long hours = 0;
        long days = 0;

        while(millis > 1000) {
            millis-=1000;
            seconds++;
        }

        while(seconds > 60) {
            seconds-=60;
            minutes++;
        }

        while(minutes > 60) {
            minutes-=60;
            hours++;
        }

        while(hours > 24) {
            hours-=24;
            days++;
        }

        String remainingMessage = BungeeBan.bungeeBan.messagesConf.getString("RemainingTime:").
                replaceAll("%days%", String.valueOf(days)).
                replaceAll("%hours%", String.valueOf(hours)).
                replaceAll("%minutes%", String.valueOf(minutes)).
                replaceAll("%seconds%", String.valueOf(seconds));

        return remainingMessage;
    }

}
