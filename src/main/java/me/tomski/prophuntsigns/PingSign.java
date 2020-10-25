package me.tomski.prophuntsigns;

import org.bukkit.*;
import org.bukkit.block.*;
import me.tomski.bungee.*;

public class PingSign
{
    int playerCountSync;
    public Location loc;
    BungeeServer server;
    Sign sign;
    
    public PingSign(final Location loc, final String server) {
        this.playerCountSync = 0;
        this.sign = (Sign)loc.getBlock().getState();
        this.server = BungeePinger.servers.get(server);
        this.loc = loc;
    }
    
    public void updateSign() {
    }
    
    public PingSignType getType() {
        if (this instanceof JoinSign) {
            return PingSignType.JOINSIGN;
        }
        if (this instanceof InfoSign) {
            return PingSignType.INFOSIGN;
        }
        return null;
    }
}
