package de.ferrixx.bungeeban.commands;

import de.ferrixx.bungeeban.BungeeBan;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class kickCommand extends Command {

    public kickCommand(BungeeBan bungeeBan) {
        super("kick", "bungeeban.kick");
    }

    @Override
    public void execute(CommandSender s, String[] strings) {
        ProxiedPlayer p = (ProxiedPlayer) s;
        ProxiedPlayer target = BungeeCord.getInstance().getPlayer(strings[0]);

        if(target == null) {
            p.sendMessage(new TextComponent(BungeeBan.prB+"§cThis player is not online§7!"));
            return;
        }

        if(strings.length == 1) {
            String kickedReason =
                    BungeeBan.bungeeBan.messagesConf.getString("KickedMessage:").
                            replaceAll("%reason%", "");
            target.disconnect(kickedReason);
        } else if(strings.length == 2) {
            String reason = strings[1];
            String kickedReason =
                    BungeeBan.bungeeBan.messagesConf.getString("KickedMessage:").
                            replaceAll("%reason%", reason);
            target.disconnect(kickedReason);
        }
    }
}
