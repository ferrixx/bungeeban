package de.ferrixx.bungeeban.listeners;

import de.ferrixx.bungeeban.BungeeBan;
import de.ferrixx.bungeeban.manager.BanManager;
import de.ferrixx.bungeeban.manager.MuteManager;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class chatListener implements Listener {


    @EventHandler
    public void onChat(ChatEvent e) {
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        String uuid = nameToUUID(p.getName());

        if(!e.getMessage().startsWith("/")) {
            if(MuteManager.isMuted(String.valueOf(uuid))) {
                e.setCancelled(true);
                String mutedReason =
                        BungeeBan.bungeeBan.messagesConf.getString("MutedMessage:").
                                replaceAll("%reason%", MuteManager.getReason(String.valueOf(uuid))).
                                replaceAll("%reamingtime%", MuteManager.getRemainingTime(String.valueOf(uuid)));
                p.sendMessage(new TextComponent(BungeeBan.prM+mutedReason));
            }
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
