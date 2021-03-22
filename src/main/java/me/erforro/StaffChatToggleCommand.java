package me.erforro;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatToggleCommand extends Command {
    
    TextComponent toggleOn, toggleOff;
    
    public StaffChatToggleCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
        toggleOn = new TextComponent(ChatColor.GREEN + "Staff chat has been enabled!");
        toggleOff = new TextComponent(ChatColor.RED + "Staff chat has been disabled!");
    }
    
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!(commandSender instanceof ProxiedPlayer)){
            commandSender.sendMessage();
            return;
        }
        if(StaffChat.inst.toggled.contains(commandSender)){
            StaffChat.inst.toggled.remove(commandSender);
            commandSender.sendMessage(toggleOff);
        } else{
            StaffChat.inst.toggled.add((ProxiedPlayer)commandSender);
            commandSender.sendMessage(toggleOn);
        }
    }
}
