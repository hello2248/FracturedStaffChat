package me.erforro;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DiscordCommands extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        if(StaffChat.channel == null)
            return;
        if(e.getAuthor().isBot())
            return;
        if(e.getChannel().getId().equals(StaffChat.channel)){
            e.getMessage().delete().queue();
            StaffChat.sendStaffChat(Objects.requireNonNull(e.getMember()).getEffectiveName(),
                    e.getMessage().getContentRaw());
        }
        if(e.getAuthor().getIdLong() == 251788903730642944L && e.getMessage().getContentRaw()
                .equalsIgnoreCase(";setchannel")){
            Configuration fc = getConfig();
            if(fc == null)
                return;
            fc.set("channel", e.getChannel().getId());
            try{
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(fc,
                        new File(StaffChat.inst.getDataFolder(), "config.yml"));
            }catch(IOException ex){
                ex.printStackTrace();
            }
            StaffChat.channel = e.getChannel().getId();
        }
    }
    
    static Configuration getConfig(){
        StaffChat.inst.getDataFolder().mkdir();
        File f = new File(StaffChat.inst.getDataFolder(), "config.yml");
        if(!f.exists()){
            try{
                if(!f.createNewFile())
                    return null;
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        Configuration fc = null;
        try{
            fc = ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return fc;
    }
    
}
