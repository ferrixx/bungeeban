package de.ferrixx.bungeeban.commands;

import de.ferrixx.bungeeban.BungeeBan;
import de.ferrixx.bungeeban.manager.MuteManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class unmuteCommand extends Command {

    public unmuteCommand(BungeeBan bungeeBan) {
        super("unmute", "bungeeban.unban");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {

        if(strings.length == 1) {
            String uuid = nameToUUID(strings[0]);
            if(MuteManager.isMuted(uuid)) {
                MuteManager.unmute(uuid);
                commandSender.sendMessage(new TextComponent(BungeeBan.prM +"§aYou unmuted the player§7!"));
            } else {
                commandSender.sendMessage(new TextComponent(BungeeBan.prM +"§cThe player isn't muted§7!"));
                return;
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
