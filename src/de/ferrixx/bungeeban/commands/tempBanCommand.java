package de.ferrixx.bungeeban.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mysql.cj.xdevapi.JsonValue;
import de.ferrixx.bungeeban.BungeeBan;
import de.ferrixx.bungeeban.manager.BanManager;
import de.ferrixx.bungeeban.manager.MuteManager;
import de.ferrixx.bungeeban.util.BanUnit;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.codehaus.plexus.util.IOUtil;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class tempBanCommand extends Command {

    public tempBanCommand(BungeeBan bungeeBan) {
        super("tempBan", "bungeeban.tempmute");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {

        if(commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) commandSender;


            if (strings.length == 4) {

                if(nameToUUID(strings[0]) == "error") {
                    p.sendMessage(new TextComponent(BungeeBan.prB+"§cThis player does not exists§7!"));
                    return;
                }

                String uuid = nameToUUID(strings[0]);

                if(BanManager.isBanned(uuid)) {
                    p.sendMessage(new TextComponent(BungeeBan.prB+"§cThis player is already banned on the server§7!"));
                    return;
                }

                long value = Long.parseLong(strings[1]);
                BanUnit unit = BanUnit.getUnit(strings[2]);
                long seconds = value * unit.getToSecond();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.uuuu");
                ZonedDateTime now = ZonedDateTime.now();

                MuteManager.mute(uuid, strings[0], strings[1], seconds, p.getDisplayName(), dtf.format(now));
                p.sendMessage(new TextComponent(BungeeBan.prB+"§aThe player " + strings[0] + " was banned from the server§7!"));


            } else {
                sendHelp(p);
            }

        }
    }

    private void sendHelp(ProxiedPlayer p) {
        p.sendMessage(new TextComponent(BungeeBan.prB+"§6Syntax §8» §7/tempban <Playername> <Number> <Unit(S|M|H|D)> <Reason>"));
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
