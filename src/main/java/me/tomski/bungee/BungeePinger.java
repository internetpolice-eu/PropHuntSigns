package me.tomski.bungee;

import org.bukkit.plugin.messaging.*;
import me.tomski.prophuntsigns.*;
import java.util.*;
import org.bukkit.entity.*;
import java.io.*;

public class BungeePinger implements PluginMessageListener
{
    private PropHuntSigns plugin;
    public static Map<String, BungeeServer> servers;
    
    static {
        BungeePinger.servers = new HashMap<String, BungeeServer>();
    }
    
    public BungeePinger(final PropHuntSigns pl) {
        this.plugin = pl;
    }
    
    public void onPluginMessageReceived(final String channel, final Player player, final byte[] b) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        final DataInputStream in = new DataInputStream(new ByteArrayInputStream(b));
        try {
            final String subchannel = in.readUTF();
            if (subchannel.equals("PropHunt")) {
                final short len = in.readShort();
                final byte[] msgbytes = new byte[len];
                in.readFully(msgbytes);
                final DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
                final String server = msgin.readUTF();
                final int max = msgin.readInt();
                final int on = msgin.readInt();
                final boolean status = msgin.readBoolean();
                final boolean canjoin = msgin.readBoolean();
                if (status) {
                    final String arena = msgin.readUTF();
                    final int time = msgin.readInt();
                    final int seekers = msgin.readInt();
                    final int hiders = msgin.readInt();
                    final int spectators = msgin.readInt();
                    final BungeeServer bs = BungeePinger.servers.get(server);
                    bs.maxPlayers = max;
                    bs.playersOnline = on;
                    bs.gameStatus = status;
                    bs.arena = arena;
                    bs.timeLeft = time;
                    bs.seekers = seekers;
                    bs.hiders = hiders;
                    bs.spectators = spectators;
                    bs.canJoin = canjoin;
                }
                else {
                    final int lobbyplayers = msgin.readInt();
                    final BungeeServer bs2 = BungeePinger.servers.get(server);
                    bs2.maxPlayers = max;
                    bs2.playersOnline = on;
                    bs2.gameStatus = status;
                    bs2.arena = "Lobby";
                    bs2.lobbyplayers = lobbyplayers;
                    bs2.canJoin = canjoin;
                }
            }
            else if (subchannel.equals("PlayerCount")) {
                final String server2 = in.readUTF();
                final int count = in.readInt();
                final BungeeServer bs3 = BungeePinger.servers.get(server2);
                if (count == 0) {
                    bs3.playersOnline = 0;
                    bs3.lobbyplayers = 0;
                    bs3.gameStatus = false;
                    bs3.lobbyplayers = 0;
                    bs3.canJoin = true;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
