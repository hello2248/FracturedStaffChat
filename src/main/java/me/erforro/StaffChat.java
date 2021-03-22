package me.erforro;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffChat extends Plugin {
    
    static StaffChat inst;
    static String channel;
    List<ProxiedPlayer> staff = new ArrayList<>();
    List<ProxiedPlayer> toggled = new ArrayList<>();
    
    static JDA jda;
    
    @Override
    public void onEnable(){
        inst = this;
        
        getProxy().getPluginManager().registerCommand(this,
                new StaffChatCommand("staffchat", "staffchat.use", "sc"));
        getProxy().getPluginManager().registerCommand(this,
                new StaffChatToggleCommand("staffchattoggle", "staffchat.use", "sct", "sctoggle"));
        getProxy().getPluginManager().registerListener(this, new ChatListener());
    
        Configuration c = DiscordCommands.getConfig();
        assert c != null;
        channel = c.getString("channel");
        try{
            jda = new JDABuilder(AccountType.BOT)
                    .setToken("DISCORD_TOKEN_HERE")
//                    .setDisabledCacheFlags(EnumSet.of(CacheFlag.ACTIVITY))
                    .setBulkDeleteSplittingEnabled(false).setCompression(Compression.NONE)
                    .setChunkingFilter(ChunkingFilter.NONE)
                    .setActivity(Activity.playing("Fractured MC")).build();
        }catch(LoginException e){
            e.printStackTrace();
        }
        
        jda.addEventListener(new DiscordCommands());
        
    }
    
    public static void sendStaffChat(ProxiedPlayer p, String t){
        TextComponent a = new TextComponent(ChatColor.DARK_AQUA + "[" +
                p.getServer().getInfo().getName() + "] " +
                p.getDisplayName() + ChatColor.AQUA + ": " + t);
        StaffChat.inst.staff.forEach(k -> k.sendMessage(a));
        if(channel == null)
            return;
        Objects.requireNonNull(jda.getTextChannelById(channel)).sendMessage(
                "``" + ChatColor.stripColor(a.getText()) + "``").queue();
    }
    
    public static void sendStaffChat(String name, String t){
        TextComponent a = new TextComponent(ChatColor.DARK_AQUA + "[discord] " +
                name + ChatColor.AQUA + ": " + t);
        StaffChat.inst.staff.forEach(k -> k.sendMessage(a));
        Objects.requireNonNull(jda.getTextChannelById(channel)).sendMessage(
                "``" + ChatColor.stripColor(a.getText()) + "``").queue();
    }
    
}
