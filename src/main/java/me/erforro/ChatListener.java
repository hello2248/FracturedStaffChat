package me.erforro;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {
    
    @SuppressWarnings("SuspiciousMethodCalls")
    @EventHandler
    public void onChat(ChatEvent e){
        if(!(e.getSender() instanceof ProxiedPlayer))
            return;
        if(e.getMessage().startsWith("/"))
            return;
        if(StaffChat.inst.toggled.contains(e.getSender()) ||
                (e.getMessage().charAt(0) == '#' && ((ProxiedPlayer)e.getSender()).hasPermission("staffchat.use"))){
            String msg = e.getMessage();
            if(msg.charAt(0) == '#')
                msg = msg.substring(1);
            e.setCancelled(true);
            StaffChat.sendStaffChat((ProxiedPlayer) e.getSender(), msg);
        }
    }
    
    @EventHandler
    public void onConnect(PostLoginEvent e){
        if(!StaffChat.inst.staff.contains(e.getPlayer()) && e.getPlayer().hasPermission("staffchat.use")){
            StaffChat.inst.staff.add(e.getPlayer());
        }
    }
    
    @EventHandler
    public void onLeave(PlayerDisconnectEvent e){
        StaffChat.inst.staff.remove(e.getPlayer());
        StaffChat.inst.toggled.remove(e.getPlayer());
    }
    
}
