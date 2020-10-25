package me.tomski.prophuntsigns;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class SignStorage
{
    private PropHuntSigns plugin;
    private String path;
    public Map<Integer, PingSign> pingSigns;
    
    public SignStorage(final PropHuntSigns phs) {
        this.path = "SignStorage";
        this.pingSigns = new HashMap<Integer, PingSign>();
        this.plugin = phs;
    }
    
    public boolean addSign(final Location loc, final PingSignType type, final String server) {
        if (type.equals(PingSignType.INFOSIGN)) {
            final InfoSign is = new InfoSign(loc, server);
            for (int i = 0; i < 1000; ++i) {
                if (!this.pingSigns.containsKey(i)) {
                    this.pingSigns.put(i, is);
                    return true;
                }
            }
        }
        if (type.equals(PingSignType.JOINSIGN)) {
            final JoinSign is2 = new JoinSign(loc, server);
            for (int i = 0; i < 1000; ++i) {
                if (!this.pingSigns.containsKey(i)) {
                    this.pingSigns.put(i, is2);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean removeSign(final Player p, final Location loc, final BlockBreakEvent e) {
        for (final int i : this.pingSigns.keySet()) {
            if (this.pingSigns.get(i).loc.equals((Object)loc)) {
                if (p.hasPermission("prophuntsigns.destroy")) {
                    this.pingSigns.remove(i);
                    return true;
                }
                e.setCancelled(true);
                return false;
            }
        }
        return false;
    }
    
    public void loadPingSignData() {
        int counter = 0;
        if (!this.plugin.getConfig().contains(this.path)) {
            return;
        }
        for (final String i : this.plugin.getConfig().getConfigurationSection(this.path).getKeys(false)) {
            final PingSign ps = this.loadPingSign(this.plugin.getConfig(), Integer.parseInt(i));
            this.pingSigns.put(Integer.parseInt(i), ps);
            ++counter;
        }
        this.plugin.getLogger().info(String.valueOf(counter) + " Ping Signs loaded");
    }
    
    public void savePingSignData() {
        int counter = 0;
        for (final int i : this.pingSigns.keySet()) {
            this.savePingSign(this.plugin.getConfig(), this.pingSigns.get(i), i);
            ++counter;
        }
        this.plugin.getLogger().info(String.valueOf(counter) + " Ping Signs saved");
        this.plugin.saveConfig();
    }
    
    private PingSign loadPingSign(final FileConfiguration config, final int i) {
        final Vector vec = config.getVector(String.valueOf(this.path) + "." + i + ".vector");
        final String worldname = config.getString(String.valueOf(this.path) + "." + i + ".worldname");
        final String type = config.getString(String.valueOf(this.path) + "." + i + ".signtype");
        final String server = config.getString(String.valueOf(this.path) + "." + i + ".server");
        final PingSignType signType = PingSignType.valueOf(type);
        PingSign ps = null;
        if (signType.equals(PingSignType.JOINSIGN)) {
            ps = new JoinSign(vec.toLocation(this.plugin.getServer().getWorld(worldname)), server);
            ps.server.canJoin = true;
        }
        else if (signType.equals(PingSignType.INFOSIGN)) {
            ps = new InfoSign(vec.toLocation(this.plugin.getServer().getWorld(worldname)), server);
        }
        return ps;
    }
    
    private void savePingSign(final FileConfiguration config, final PingSign ps, final int i) {
        config.set(String.valueOf(this.path) + "." + i + ".vector", (Object)ps.loc.toVector());
        config.set(String.valueOf(this.path) + "." + i + ".worldname", (Object)ps.loc.getWorld().getName());
        config.set(String.valueOf(this.path) + "." + i + ".signtype", (Object)ps.getType().stringValue());
        config.set(String.valueOf(this.path) + "." + i + ".server", (Object)ps.server.name);
    }
}
