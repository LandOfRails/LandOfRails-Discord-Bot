package listener;

import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.gson.Gson;
import commands.utils.LauncherModpackUtils;
import model.Modpack;
import model.Triple;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import storage.Container;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
//        event.getTextChannel().sendMessage(event.getReaction() + event.getReactionEmote().getAsCodepoints()).queue();
//        }

        //Roles Management
        if (event.getMessageId().equals("712375636009943070")) {
            lmr = event.getTextChannel().retrieveMessageById("712375636009943070").complete().getReactions();
            switch (event.getReactionEmote().getAsCodepoints()) {
                //IR
                case "U+1f1ee":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(712376407790977024L)).complete();
                    updateIR();
                    break;
                //TC
                case "U+1f1f9":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(712376489580036178L)).complete();
                    updateTC();
                    break;
                //ZnD
                case "U+1f1ff":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(712376522257989803L)).complete();
                    updateZnD();
                    break;
                //RTM
                case "U+1f1f7":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(712376551622049882L)).complete();
                    updateRTM();
                    break;
                //TTT
                case "U+1f1ec":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(723908789648097284L)).complete();
                    updateTTT();
                    break;
                //OpenTTD
                case "U+1f1f4":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(723908821642379345L)).complete();
                    updateOpenTTD();
                    break;
                //Alpha
                case "U+1f1e6":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(744099514088030209L)).complete();
                    break;
                //LandOfSignals
                case "U+1f1f1":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(797155560725676052L)).complete();
                    break;
                default:
                    break;
            }
        }

        //Bot confirmation
        if (event.getMessageId().equals("726557812796424283") && event.getReactionEmote().getName().equals("hmmm")) {
            //Change roles
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(526509036859031552L)).complete();
            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(726558387625787452L)).complete();

            //Delete reaction
            event.getReaction().removeReaction(event.getUser()).complete();
        }

        if (event.getMessageIdLong() == 794590774566191145L && event.getReactionEmote().getAsCodepoints().equals("U+2705")) {
            try {
                Connection conn = Container.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM teamRulesAccepted WHERE MemberID=" + event.getMember().getId());
                rs.last();
                DateFormat dtf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                if (rs.getRow() == 0) {
                    Timestamp time = new Timestamp(System.currentTimeMillis());
                    PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO teamRulesAccepted (MemberID, MemberUsername, Timestamp) VALUES (" + event.getMember().getId() + ", '" + event.getUser().getName() + "', ?)");
                    stmt2.setTimestamp(1, time);
                    stmt2.execute();
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " accepted the rules on " + dtf.format(time)).complete().delete().queueAfter(10, TimeUnit.SECONDS);
                    event.getReaction().removeReaction(event.getUser()).complete();
                    stmt2.close();
                } else {
                    event.getReaction().removeReaction(event.getUser()).complete();
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " already accepted the rules on " + dtf.format(rs.getDate("Timestamp"))).complete().delete().queueAfter(10, TimeUnit.SECONDS);
                }
                stmt.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (!Container.modpackDeletionList.isEmpty()) {
            for (Triple<Message, Long, Modpack> p : Container.modpackDeletionList) {
                if (p.getLeft().getIdLong() == event.getMessageIdLong() && p.getMiddle() == event.getMember().getIdLong()) {
                    p.getLeft().clearReactions().queue();
                    if (event.getReactionEmote().getAsCodepoints().equals("U+2705")) {
                        List<Modpack> modpackList = LauncherModpackUtils.getModpackList();
                        modpackList.remove(p.getRight());
                        try (FileWriter fw = new FileWriter("/var/www/launcher/ModpackList.json"); BufferedWriter bw = new BufferedWriter(fw)) {
                            bw.write(new Gson().toJson(modpackList));
                            bw.flush();
                        } catch (final Exception e) {
                            e.printStackTrace();
                        }
                        event.getChannel().sendMessage("Modpack \"" + p.getRight().getTitle() + "\" gelöscht.").queue();
                    } else {
                        event.getChannel().sendMessage("Löschen abgebrochen.").queue();
                    }
                }
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
                //LandOfSignals
                case "U+1f1f1":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(797155560725676052L)).complete();
                    break;
                default:
                    break;
            }
        }
    }
}
