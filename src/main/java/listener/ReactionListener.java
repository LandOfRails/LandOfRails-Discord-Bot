package listener;

import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import storage.Container;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ReactionListener extends ListenerAdapter {

    private List<MessageReaction> lmr = null;

    private void updateIR() {
        for (MessageReaction mr : lmr) {
            if (mr.getReactionEmote().getAsCodepoints().equals("U+1f1ee")) {
                try {
                    UpdateValuesResponse result = Container.sheetsService.spreadsheets().values().update(Container.spreadsheetId, "B2", new ValueRange().setValues(Arrays.asList(Arrays.asList(mr.getCount())))).setValueInputOption("RAW").execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void updateTC() {
        for (MessageReaction mr : lmr) {
            if (mr.getReactionEmote().getAsCodepoints().equals("U+1f1f9")) {
                try {
                    UpdateValuesResponse result = Container.sheetsService.spreadsheets().values().update(Container.spreadsheetId, "B3", new ValueRange().setValues(Arrays.asList(Arrays.asList(mr.getCount())))).setValueInputOption("RAW").execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void updateZnD() {
        for (MessageReaction mr : lmr) {
            if (mr.getReactionEmote().getAsCodepoints().equals("U+1f1ff")) {
                try {
                    UpdateValuesResponse result = Container.sheetsService.spreadsheets().values().update(Container.spreadsheetId, "B4", new ValueRange().setValues(Arrays.asList(Arrays.asList(mr.getCount())))).setValueInputOption("RAW").execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void updateRTM() {
        for (MessageReaction mr : lmr) {
            if (mr.getReactionEmote().getAsCodepoints().equals("U+1f1f7")) {
                try {
                    UpdateValuesResponse result = Container.sheetsService.spreadsheets().values().update(Container.spreadsheetId, "B5", new ValueRange().setValues(Arrays.asList(Arrays.asList(mr.getCount())))).setValueInputOption("RAW").execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void updateTTT() {
        for (MessageReaction mr : lmr) {
            if (mr.getReactionEmote().getAsCodepoints().equals("U+1f1ec")) {
                try {
                    UpdateValuesResponse result = Container.sheetsService.spreadsheets().values().update(Container.spreadsheetId, "B7", new ValueRange().setValues(Arrays.asList(Arrays.asList(mr.getCount())))).setValueInputOption("RAW").execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void updateOpenTTD() {
        for (MessageReaction mr : lmr) {
            if (mr.getReactionEmote().getAsCodepoints().equals("U+1f1f4")) {
                try {
                    UpdateValuesResponse result = Container.sheetsService.spreadsheets().values().update(Container.spreadsheetId, "B8", new ValueRange().setValues(Arrays.asList(Arrays.asList(mr.getCount())))).setValueInputOption("RAW").execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {

//        if (event.getTextChannel().equals(event.getGuild().getTextChannelById("726490100082409492"))) {
//            event.getTextChannel().sendMessage(event.getReaction() + event.getReactionEmote().getAsCodepoints()).queue();
//        }

        //Roles Management
        if (event.getMessageId().equals("712375636009943070")) {
            lmr = event.getTextChannel().retrieveMessageById("712375636009943070").complete().getReactions();
            switch (event.getReactionEmote().getAsCodepoints()) {
                //IR
                case "U+1f1ee":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("712376407790977024")).complete();
                    updateIR();
                    break;
                //TC
                case "U+1f1f9":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("712376489580036178")).complete();
                    updateTC();
                    break;
                //ZnD
                case "U+1f1ff":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("712376522257989803")).complete();
                    updateZnD();
                    break;
                //RTM
                case "U+1f1f7":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("712376551622049882")).complete();
                    updateRTM();
                    break;
                //TTT
                case "U+1f1ec":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("723908789648097284")).complete();
                    updateTTT();
                    break;
                //OpenTTD
                case "U+1f1f4":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("723908821642379345")).complete();
                    updateOpenTTD();
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
            lmr = event.getTextChannel().retrieveMessageById("712375636009943070").complete().getReactions();
            switch (event.getReactionEmote().getAsCodepoints()) {
                //IR
                case "U+1f1ee":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("712376407790977024")).complete();
                    updateIR();
                    break;
                //TC
                case "U+1f1f9":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("712376489580036178")).complete();
                    updateTC();
                    break;
                //ZnD
                case "U+1f1ff":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("712376522257989803")).complete();
                    updateZnD();
                    break;
                //RTM
                case "U+1f1f7":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("712376551622049882")).complete();
                    updateRTM();
                    break;
                //TTT
                case "U+1f1ec":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("723908789648097284")).complete();
                    updateTTT();
                    break;
                //OpenTTD
                case "U+1f1f4":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("723908821642379345")).complete();
                    updateOpenTTD();
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
