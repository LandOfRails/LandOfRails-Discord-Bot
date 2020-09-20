package listener;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ServerListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        System.out.println(event.getMember() + " has joined the discord and is now a potentional bot.");
        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("726558387625787452")).queue();
    }

    //    @Override
//    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
//        System.out.println(event.getMember() + " has joined the discord and is now a potentional bot.");
//        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("726558387625787452")).queue();
//    }
}
