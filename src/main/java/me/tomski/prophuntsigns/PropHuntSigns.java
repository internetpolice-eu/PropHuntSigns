package me.tomski.prophuntsigns;

import org.bukkit.plugin.java.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.messaging.*;
import org.bukkit.event.*;
import me.tomski.bungee.*;
import java.util.*;
import org.bukkit.command.*;
import org.bukkit.*;
import java.io.*;

public class PropHuntSigns extends JavaPlugin
{
    public SignStorage SS;
    
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.initServers();
        this.getServer().getMessenger().registerOutgoingPluginChannel((Plugin)this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel((Plugin)this, "BungeeCord", (PluginMessageListener)new BungeePinger(this));
        this.getServer().getPluginManager().registerEvents((Listener)new SignListener(this), (Plugin)this);
        (this.SS = new SignStorage(this)).loadPingSignData();
        this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new SignUpdater(this), 20L, 20L);
        this.getLogger().info("PropHunSigns updaters loaded");
    }
    
    public void onDisable() {
        this.SS.savePingSignData();
    }
    
    private void initServers() {
        for (final String s : this.getConfig().getStringList("bungee-server-names")) {
            final BungeeServer bs = new BungeeServer(s, this);
            BungeePinger.servers.put(s, bs);
        }
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("prophuntsigns") && sender.hasPermission("prophuntsigns.reload") && args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.initServers();
            sender.sendMessage(ChatColor.GREEN + "Config reloaded");
            return true;
        }
        return false;
    }
    
    public void sendCountRequest(final String server) throws IOException {
        if (this.getServer().getOnlinePlayers().length > 0) {
            final ByteArrayOutputStream b = new ByteArrayOutputStream();
            final DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("PlayerCount");
            out.writeUTF(server);
            this.getServer().getOnlinePlayers()[0].sendPluginMessage((Plugin)this, "BungeeCord", b.toByteArray());
        }
    }
}
