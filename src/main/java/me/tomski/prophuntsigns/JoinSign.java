package me.tomski.prophuntsigns;

import org.bukkit.*;

public class JoinSign extends PingSign
{
    public JoinSign(final Location loc, final String server) {
        super(loc, server);
    }
    
    @Override
    public void updateSign() {
        this.sign.setLine(0, ChatColor.translateAlternateColorCodes('&', "&3[&4PH Join&3]"));
        if (this.server.gameStatus) {
            this.sign.setLine(1, ChatColor.translateAlternateColorCodes('&', "&3&lIn Progress!"));
        }
        else {
            this.sign.setLine(1, ChatColor.translateAlternateColorCodes('&', "&2&lIn Lobby!"));
        }
        if (this.server.playersOnline == 0) {
            this.sign.setLine(2, ChatColor.translateAlternateColorCodes('&', "&3Empty!"));
        }
        else {
            this.sign.setLine(2, ChatColor.translateAlternateColorCodes('&', "&3" + this.server.playersOnline + "&f/&1" + this.server.maxPlayers));
        }
        if (!this.server.canJoin) {
            this.sign.setLine(3, ChatColor.translateAlternateColorCodes('&', "&4[NotJoinable]"));
        }
        else if (this.server.playersOnline == 0) {
            this.sign.setLine(3, ChatColor.translateAlternateColorCodes('&', "&5&l[JOIN!]"));
        }
        else if (this.server.playersOnline == this.server.maxPlayers) {
            this.sign.setLine(3, ChatColor.translateAlternateColorCodes('&', "&4&lFULL!"));
        }
        else {
            this.sign.setLine(3, ChatColor.translateAlternateColorCodes('&', "&5&l[JOIN!]"));
        }
        this.sign.update();
    }
}
