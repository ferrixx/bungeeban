package de.ferrixx.bungeeban.listeners;

import de.ferrixx.bungeeban.BungeeBan;
import de.ferrixx.bungeeban.manager.BanManager;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class loginListener implements Listener {

    @EventHandler
    public void onJoin(PreLoginEvent e) {
        String uuid = nameToUUID(e.getConnection().getName());
        if(BanManager.isBanned(uuid)) {
            e.setCancelled(true);
            String bannedReason =
                    BungeeBan.bungeeBan.messagesConf.getString("BannedMessage:").
                            replaceAll("%reason%", BanManager.getReason(uuid)).
                            replaceAll("%reamingtime%", BanManager.getRemainingTime(uuid));
            e.setCancelReason(bannedReason);
        }
    }


    public static String nameToUUID(String playerName) {
        try {
            String urlString = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();

            return readInputStreamAsString(is);

        } catch (IOException e) {
        }
        return "error";
    }

    public static String readInputStreamAsString(InputStream in)
            throws IOException {

        BufferedInputStream bis = new BufferedInputStream(in);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while(result != -1) {
            byte b = (byte)result;
            buf.write(b);
            result = bis.read();
        }
        return buf.toString();
    }

}
