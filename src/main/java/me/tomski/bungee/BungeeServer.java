package me.tomski.bungee;

import me.tomski.prophuntsigns.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;
import java.io.*;

public class BungeeServer
{
    public int maxPlayers;
    public int playersOnline;
    public int seekers;
    public int hiders;
    public int spectators;
    public int lobbyplayers;
    public boolean gameStatus;
    public String arena;
    public int timeLeft;
    public String name;
    private PropHuntSigns plugin;
    public boolean canJoin;
    
    public BungeeServer(final String servername, final PropHuntSigns plugin) {
        this.maxPlayers = 0;
        this.playersOnline = 0;
        this.seekers = 0;
        this.hiders = 0;
        this.spectators = 0;
        this.lobbyplayers = 0;
        this.timeLeft = -1;
        this.canJoin = true;
        this.plugin = plugin;
        this.name = servername;
    }
    
    public void connectToServer(final Player p) throws IOException {
        if (this.canJoin) {
            final ByteArrayOutputStream b = new ByteArrayOutputStream();
            final DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(this.name);
            p.sendPluginMessage((Plugin)this.plugin, "BungeeCord", b.toByteArray());
        }
    }
}
