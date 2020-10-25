package me.tomski.prophuntsigns;

import org.bukkit.event.player.*;
import org.bukkit.*;
import java.util.*;
import java.io.*;
import org.bukkit.event.*;
import me.tomski.bungee.*;
import org.bukkit.event.block.*;

public class SignListener implements Listener
{
    private PropHuntSigns plugin;
    
    public SignListener(final PropHuntSigns plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) throws IOException {
        if ((e.getAction().equals((Object)Action.RIGHT_CLICK_BLOCK) || e.getAction().equals((Object)Action.LEFT_CLICK_BLOCK)) && e.getClickedBlock().getType().equals((Object)Material.WALL_SIGN) && e.getPlayer().hasPermission("prophuntsigns.use.join")) {
            for (final int i : this.plugin.SS.pingSigns.keySet()) {
                if (this.plugin.SS.pingSigns.get(i).loc.equals((Object)e.getClickedBlock().getLocation())) {
                    if (this.plugin.SS.pingSigns.get(i).server.maxPlayers > this.plugin.SS.pingSigns.get(i).server.playersOnline || this.plugin.SS.pingSigns.get(i).server.playersOnline == 0 || e.getPlayer().hasPermission("prophunt.joinoverride")) {
                        if (!(this.plugin.SS.pingSigns.get(i) instanceof JoinSign)) {
                            continue;
                        }
                        this.plugin.SS.pingSigns.get(i).server.connectToServer(e.getPlayer());
                    }
                    else {
                        SignMessaging.sendMessage(e.getPlayer(), "That game is full!");
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onSignChange(final SignChangeEvent e) {
        if (e.getBlock().getType().equals((Object)Material.WALL_SIGN)) {
            if (e.getPlayer().hasPermission("prophuntsigns.place.join")) {
                final String[] lines = e.getLines();
                if (lines[0].equalsIgnoreCase("[prophunt]")) {
                    final String server = lines[1];
                    if (BungeePinger.servers.containsKey(server) && lines[2].equalsIgnoreCase("join")) {
                        this.plugin.SS.addSign(e.getBlock().getLocation(), PingSignType.JOINSIGN, server);
                        SignMessaging.sendMessage(e.getPlayer(), "Join Sign created for the server: " + server);
                    }
                }
            }
            if (e.getPlayer().hasPermission("prophuntsigns.place.info")) {
                final String[] lines = e.getLines();
                if (lines[0].equalsIgnoreCase("[prophunt]")) {
                    final String server = lines[1];
                    if (BungeePinger.servers.containsKey(server) && lines[2].equalsIgnoreCase("info")) {
                        this.plugin.SS.addSign(e.getBlock().getLocation(), PingSignType.INFOSIGN, server);
                        SignMessaging.sendMessage(e.getPlayer(), "Info Sign created for the server: " + server);
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onSignDelete(final BlockBreakEvent e) {
        if (!e.getBlock().getType().equals((Object)Material.WALL_SIGN)) {
            return;
        }
        if (this.plugin.SS.removeSign(e.getPlayer(), e.getBlock().getLocation(), e)) {
            SignMessaging.sendMessage(e.getPlayer(), "You have removed a Ping Sign");
        }
    }
}
