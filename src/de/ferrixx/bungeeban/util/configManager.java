package de.ferrixx.bungeeban.util;

import de.ferrixx.bungeeban.BungeeBan;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class configManager {

    static File prFile = new File(BungeeBan.bungeeBan.getDataFolder(), "prefixes.yml");
    static File mysqlFile = new File(BungeeBan.bungeeBan.getDataFolder(), "mysql.yml");
    static File messagesFile = new File(BungeeBan.bungeeBan.getDataFolder(), "messages.yml");

    public static void makeConfigs() throws IOException {
        if(!BungeeBan.bungeeBan.getDataFolder().exists()) {
            BungeeBan.bungeeBan.getDataFolder().mkdir();
        }

        if(!prFile.exists()) {
            Files.createFile(prFile.toPath());
            BungeeBan.bungeeBan.prefixes = ConfigurationProvider.getProvider(YamlConfiguration.class).
                    load(new File(BungeeBan.bungeeBan.getDataFolder(), "prefixes.yml"));
            BungeeBan.bungeeBan.prefixes.set("Ban:", "§8» §4BungeeBan §8┃ §7");
            BungeeBan.bungeeBan.prefixes.set("Mute:", "§8» §4BungeeMute §8┃ §7");
            BungeeBan.bungeeBan.prefixes.set("No Perm:", "§8» §cPermission §8┃ No Permissions to perfom this Command!");
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(BungeeBan.bungeeBan.prefixes, prFile);
        } else {
            BungeeBan.bungeeBan.prefixes = ConfigurationProvider.getProvider(YamlConfiguration.class).
                    load(new File(BungeeBan.bungeeBan.getDataFolder(), "prefixes.yml"));
        }

        if(!mysqlFile.exists()) {
            Files.createFile(mysqlFile.toPath());
            BungeeBan.bungeeBan.mysqlConf = ConfigurationProvider.getProvider(YamlConfiguration.class).
                    load(new File(BungeeBan.bungeeBan.getDataFolder(), "mysql.yml"));
            BungeeBan.bungeeBan.mysqlConf.set("Host:", "localhost");
            BungeeBan.bungeeBan.mysqlConf.set("Database:", "bungeeban");
            BungeeBan.bungeeBan.mysqlConf.set("User:", "root");
            BungeeBan.bungeeBan.mysqlConf.set("Password:", "");
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(BungeeBan.bungeeBan.mysqlConf, mysqlFile);
        } else {
            BungeeBan.bungeeBan.mysqlConf = ConfigurationProvider.getProvider(YamlConfiguration.class).
                    load(new File(BungeeBan.bungeeBan.getDataFolder(), "mysql.yml"));
        }

        if(!messagesFile.exists()) {
            Files.createFile(messagesFile.toPath());
            BungeeBan.bungeeBan.messagesConf = ConfigurationProvider.getProvider(YamlConfiguration.class).
                    load(new File(BungeeBan.bungeeBan.getDataFolder(), "messages.yml"));
            BungeeBan.bungeeBan.messagesConf.set("BannedMessage:", "§cYou are banned from the Network§8.\n\n §3Reason§8: §e%reason% \n\n Remaining Time§8: §e%reamingtime%");
            BungeeBan.bungeeBan.messagesConf.set("MutedMessage:", "§cYou are muted from the Network§8.\n\n §3Reason§8: §e%reason% \n\n Remaining Time§8: §e%reamingtime%");
            BungeeBan.bungeeBan.messagesConf.set("RemainingTime:", "§d%days% §eDay(s) §d%hours% §eHour(s) §d%minutes% §eMinute(s) ");
            BungeeBan.bungeeBan.messagesConf.set("PermanedBanned:", "§cPERMANED");
            BungeeBan.bungeeBan.messagesConf.set("KickedMessage:", "§cYou were kicked from the server %reason$");
            BungeeBan.bungeeBan.messagesConf.set("Seconds:", "Second(s)");
            BungeeBan.bungeeBan.messagesConf.set("Minutes:", "Minute(s)");
            BungeeBan.bungeeBan.messagesConf.set("Hours:", "Hour(s)");
            BungeeBan.bungeeBan.messagesConf.set("Days:", "Day(s)");
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(BungeeBan.bungeeBan.messagesConf, messagesFile);
        } else {
            BungeeBan.bungeeBan.messagesConf = ConfigurationProvider.getProvider(YamlConfiguration.class).
                    load(new File(BungeeBan.bungeeBan.getDataFolder(), "messages.yml"));
        }


    }

}
