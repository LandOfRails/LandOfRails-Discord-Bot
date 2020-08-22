package listener;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ReactionListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {

//        if (event.getTextChannel().equals(event.getGuild().getTextChannelById("726490100082409492"))) {
//            event.getTextChannel().sendMessage(event.getReaction() + event.getReactionEmote().getAsCodepoints()).queue();
//        }

        //Roles Management
        if (event.getMessageId().equals("712375636009943070")) {
            switch (event.getReactionEmote().getAsCodepoints()) {
                //IR
                case "U+1f1ee":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("712376407790977024")).complete();
                    break;
                //TC
                case "U+1f1f9":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("712376489580036178")).complete();
                    break;
                //ZnD
                case "U+1f1ff":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("712376522257989803")).complete();
                    break;
                //RTM
                case "U+1f1f7":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("712376551622049882")).complete();
                    break;
                //TTT
                case "U+1f1ec":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("723908789648097284")).complete();
                    break;
                //OpenTTD
                case "U+1f1f4":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("723908821642379345")).complete();
                    break;
                //Alpha
                case "U+1f1e6":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("744099514088030209")).complete();
                    break;
                default:
                    break;
            }
        }

        //Bot confirmation
        if (event.getMessageId().equals("726557812796424283")) {
            if (event.getReactionEmote().getName().equals("hmmm")) {
                //Change roles
                event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(526509036859031552L)).complete();
                event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(726558387625787452L)).complete();

                //Delete reaction
                event.getReaction().removeReaction(event.getUser()).complete();
            }
        }
    }

    @Override
    public void onMessageReactionRemove(@Nonnull MessageReactionRemoveEvent event) {

        //Roles Management
        if (event.getMessageId().equals("712375636009943070")) {
            switch (event.getReactionEmote().getAsCodepoints()) {
                //IR
                case "U+1f1ee":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("712376407790977024")).complete();
                    break;
                //TC
                case "U+1f1f9":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("712376489580036178")).complete();
                    break;
                //ZnD
                case "U+1f1ff":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("712376522257989803")).complete();
                    break;
                //RTM
                case "U+1f1f7":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("712376551622049882")).complete();
                    break;
                //TTT
                case "U+1f1ec":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("723908789648097284")).complete();
                    break;
                //OpenTTD
                case "U+1f1f4":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("723908821642379345")).complete();
                    break;
                //Alpha
                case "U+1f1e6":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("744099514088030209")).complete();
                    break;
                default:
                    break;
            }
        }
    }
}
