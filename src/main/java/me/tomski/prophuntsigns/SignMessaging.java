package me.tomski.prophuntsigns;

import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;

public class SignMessaging
{
    private static String prefix;
    private static String banner;
    
    static {
        SignMessaging.prefix = "&0[&6Prop&4HuntSigns&0]: &a";
        SignMessaging.banner = "&60o0&0_______ &bPropHuntSigns &0_______&60o0";
    }
    
    public static void sendMessage(final Player p, final String msg) {
        final String finalmsg = parseChatColors(String.valueOf(SignMessaging.prefix) + msg);
        p.sendMessage(finalmsg);
    }
    
    public static void broadcastMessage(final String msg) {
        final String finalmsg = parseChatColors(String.valueOf(SignMessaging.prefix) + msg);
        Bukkit.broadcastMessage(finalmsg);
    }
    
    public static void broadcastMessageToPlayers(final List<String> hiders, final List<String> seekers, final String msg) {
        for (final String hider : hiders) {
            if (Bukkit.getServer().getPlayer(hider) != null) {
                sendMessage(Bukkit.getServer().getPlayer(hider), msg);
            }
        }
        for (final String seeker : seekers) {
            if (Bukkit.getServer().getPlayer(seeker) != null) {
                sendMessage(Bukkit.getServer().getPlayer(seeker), msg);
            }
        }
    }
    
    private static String parseChatColors(final String m) {
        return m.replaceAll("&", "\u00A7");
    }
}
