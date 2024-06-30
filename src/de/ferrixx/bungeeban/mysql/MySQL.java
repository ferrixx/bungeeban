package de.ferrixx.bungeeban.mysql;

import de.ferrixx.bungeeban.BungeeBan;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

    private static String HOST = (String) BungeeBan.bungeeBan.mysqlConf.get("Host:");
    private static String DATABASE = (String) BungeeBan.bungeeBan.mysqlConf.get("Database:");
    private static String USER = (String) BungeeBan.bungeeBan.mysqlConf.get("User:");
    private static String PASSWORD = (String) BungeeBan.bungeeBan.mysqlConf.get("Password:");
    public static Connection con;

    public static void connect() throws SQLException {
        if(!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://"+HOST+":3306/"+DATABASE+"?autoReconnect=true", USER, PASSWORD);
                BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§8» §4BungeeBan §8┃ §7§aDatabase connected."));
            } catch (SQLException e) {
                BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§8» §4BungeeBan §8┃ §7§cError connecting to Database."));
                e.printStackTrace();
            }
        }
    }

    public static void close() {
        if(isConnected()) {
            try {
                con.close();
                BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§8» §4BungeeBan §8┃ §7§aDatabase closed."));
            } catch (SQLException e) {
                BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§8» §4BungeeBan §8┃ §7§cError closing the Database."));
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean isConnected() { return con != null; }

    public static void createTable() {
        if(isConnected()) {
            try {
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS bans (id INT(10) AUTO_INCREMENT, name VARCHAR(100), uuid VARCHAR(100), end VARCHAR(100), reason VARCHAR(100), bannedby VARCHAR(100), date VARCHAR(100), PRIMARY KEY(`id`))");
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS mutes (id INT(10) AUTO_INCREMENT, name VARCHAR(100), uuid VARCHAR(100), end VARCHAR(100), reason VARCHAR(100), mutedBy VARCHAR(100), date VARCHAR(100), PRIMARY KEY(`id`))");
            } catch (SQLException e) {
                BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§8» §4BungeeBan §8┃ §7§cError creating the Database Tables."));
                e.printStackTrace();
            }
        }
    }

    public static void update(String qry) {
        if(isConnected()) {
            try {
                con.createStatement().executeUpdate(qry);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static ResultSet getResultSet(String qry) {
        if(isConnected()) {
            try {
                return con.createStatement().executeQuery(qry);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
