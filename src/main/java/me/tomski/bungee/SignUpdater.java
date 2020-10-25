package me.tomski.bungee;

import java.io.*;
import me.tomski.prophuntsigns.*;
import java.util.*;

public class SignUpdater implements Runnable
{
    PropHuntSigns phs;
    
    public SignUpdater(final PropHuntSigns plugin) {
        this.phs = plugin;
    }
    
    @Override
    public void run() {
        for (final String server : BungeePinger.servers.keySet()) {
            try {
                this.phs.sendCountRequest(server);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (final PingSign ps : this.phs.SS.pingSigns.values()) {
            ps.updateSign();
        }
    }
}
