package me.erforro;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCommand extends Command {
    
    
    public StaffChatCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }
    
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        StringBuilder b = new StringBuilder();
        if(strings.length == 0)
            return;
        for(String k : strings){
            b.append(k).append(' ');
        }
        b.deleteCharAt(b.length() - 1);
        StaffChat.sendStaffChat((ProxiedPlayer)commandSender, b.toString());
    }
}
