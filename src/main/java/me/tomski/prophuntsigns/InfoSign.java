package me.tomski.prophuntsigns;

import org.bukkit.*;

public class InfoSign extends PingSign
{
    public InfoSign(final Location loc, final String server) {
        super(loc, server);
    }
    
    @Override
    public void updateSign() {
        this.sign.setLine(0, ChatColor.translateAlternateColorCodes('&', "&3[&4PH Info&3]"));
        if (this.server.gameStatus) {
            this.sign.setLine(1, ChatColor.translateAlternateColorCodes('&', "&5&l" + this.server.arena));
        }
        else {
            this.sign.setLine(1, ChatColor.translateAlternateColorCodes('&', "&2&lLobby!"));
        }
        if (this.server.gameStatus) {
            this.sign.setLine(2, ChatColor.translateAlternateColorCodes('&', "&4" + this.server.seekers + ":&b" + this.server.hiders + ":&8" + this.server.spectators));
        }
        else {
            this.sign.setLine(2, ChatColor.translateAlternateColorCodes('&', "&1Lobby: &3" + this.server.lobbyplayers));
        }
        if (this.server.gameStatus) {
            this.sign.setLine(3, ChatColor.translateAlternateColorCodes('&', "&4Time: &4&l" + this.server.timeLeft));
        }
        else {
            this.sign.setLine(3, ChatColor.translateAlternateColorCodes('&', "&b&k!!!!!!!!!!!"));
        }
        this.sign.update();
    }
}
