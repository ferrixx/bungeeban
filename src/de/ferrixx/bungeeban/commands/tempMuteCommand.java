package de.ferrixx.bungeeban.commands;

import de.ferrixx.bungeeban.BungeeBan;
import de.ferrixx.bungeeban.manager.BanManager;
import de.ferrixx.bungeeban.manager.MuteManager;
import de.ferrixx.bungeeban.util.BanUnit;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class tempMuteCommand extends Command {

    public tempMuteCommand(BungeeBan bungeeBan) {
        super("tempmute", "bungeeban.tempban");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {

        if(commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) commandSender;


            if (strings.length == 4) {

                if(nameToUUID(strings[0]) == "error") {
                    p.sendMessage(new TextComponent(BungeeBan.prM+"§cThis player does not exists§7!"));
                    return;
                }

                String uuid = nameToUUID(strings[0]);

                if(MuteManager.isMuted(uuid)) {
                    p.sendMessage(new TextComponent(BungeeBan.prM+"§cThis player is already muted on the server§7!"));
                    return;
                }

                long value = Long.parseLong(strings[1]);
                BanUnit unit = BanUnit.getUnit(strings[2]);
                long seconds = value * unit.getToSecond();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.uuuu");
                ZonedDateTime now = ZonedDateTime.now();

                MuteManager.mute(uuid, strings[0], strings[1], seconds, p.getDisplayName(), dtf.format(now));
                p.sendMessage(new TextComponent(BungeeBan.prM+"§aThe player " + strings[0] + " was muted on the server§7!"));


            } else {
                sendHelp(p);
            }

        }
    }

    private void sendHelp(ProxiedPlayer p) {
        p.sendMessage(new TextComponent(BungeeBan.prM+"§6Syntax §8» §7/tempmute <Playername> <Number> <Unit(S|M|H|D)> <Reason>"));
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
