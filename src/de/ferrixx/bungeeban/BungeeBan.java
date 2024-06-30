package de.ferrixx.bungeeban;


import de.ferrixx.bungeeban.commands.*;
import de.ferrixx.bungeeban.listeners.chatListener;
import de.ferrixx.bungeeban.listeners.loginListener;
import de.ferrixx.bungeeban.mysql.MySQL;
import de.ferrixx.bungeeban.util.configManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 *
 * Created by Justin I. on 28.06.2024
 * Last edited on: 28.06.2024
 *
 */

public class BungeeBan extends Plugin {

    /* INSTANCE */
    public static BungeeBan bungeeBan;

    public Configuration prefixes;
    public Configuration mysqlConf;
    public Configuration messagesConf;

    /* PREFIXES */
    public static String prB;
    public static String prM;
    public static String noPerm;

    /* ENABLE */

    @Override
    public void onEnable() {
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§8» §4BungeeBan §8┃ §7§eLoading Plugin§7..."));
        try {
            bungeeBan = this;
            configManager.makeConfigs();
            MySQL.connect();
            MySQL.createTable();
            setPrefixes();
            load();
            BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§8» §4BungeeBan §8┃ §7§aSucessfully Loaded Plugin§7!"));
        }catch (Exception e) {
            BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§8» §4BungeeBan §8┃ §7§cError loading the Plugin§7!"));
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {}

    public void setPrefixes() {
        prB = (String) prefixes.get("Ban:");
        prM = (String) prefixes.get("Mute:");
        noPerm = (String) prefixes.get("No Perm:");
    }

    private void load() {
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new banCommand(this));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new kickCommand(this));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new tempBanCommand(this));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new tempMuteCommand(this));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new muteCommand(this));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new unbanCommand(this));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new unmuteCommand(this));
        BungeeCord.getInstance().getPluginManager().registerListener(this, new loginListener());
        BungeeCord.getInstance().getPluginManager().registerListener(this, new chatListener());
    }

}
