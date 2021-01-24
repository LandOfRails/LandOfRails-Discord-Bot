package listener;

import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.gson.Gson;
import commands.utils.LauncherModpackUtils;
import model.Modpack;
import model.Triple;
import net.dv8tion.jda.api.entities.Member;
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
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ReactionListener extends ListenerAdapter {

    private List<MessageReaction> lmr = null;

    /**
     * <MessageID, HashMap<EmojiCodepoints, RoleID>>
     */
    private static final HashMap<Long, HashMap<String, Long>> rolesList = new HashMap<Long, HashMap<String, Long>>() {{
        //R O L E S
        put(712375636009943070L, new HashMap<String, Long>() {{
            //IR
            put("U+1f1ee", 712376407790977024L);
            //TC
            put("U+1f1f9", 712376489580036178L);
            //ZnD
            put("U+1f1ff", 712376522257989803L);
            //RTM
            put("U+1f1f7", 712376551622049882L);
            //TTT
            put("U+1f1ec", 723908789648097284L);
            //OpenTTD
            put("U+1f1f4", 723908821642379345L);
            //Alpha
            put("U+1f1e6", 744099514088030209L);
            //LandOfSignals
            put("U+1f1f1", 797155560725676052L);
        }});

        //O T H E R
    }};

    private static final long channelID = 575451408195780618L;

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
        Member member = event.getMember();
        Long messageID = event.getMessageIdLong();
        if (member != null && rolesList.containsKey(messageID) && !event.getUser().isBot()) {
            lmr = event.getTextChannel().retrieveMessageById(712375636009943070L).complete().getReactions();
            String codepoints = event.getReactionEmote().getAsCodepoints();
            HashMap<String, Long> roles = rolesList.get(messageID);
            updateIR();
            updateOpenTTD();
            updateRTM();
            updateTC();
            updateZnD();
            updateTTT();
            if (roles.containsKey(codepoints)) {
                event.getGuild().addRoleToMember(member, event.getGuild().getRoleById(roles.get(codepoints))).complete();
            }
        }

        //Bot confirmation
        if (event.getMessageIdLong() == 726557812796424283L && event.getReactionEmote().getName().equals("hmmm")) {
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
        Member member = event.getMember();
        Long messageID = event.getMessageIdLong();
        if (member != null && rolesList.containsKey(messageID) && !event.getUser().isBot()) {
            lmr = event.getTextChannel().retrieveMessageById(712375636009943070L).complete().getReactions();
            String codepoints = event.getReactionEmote().getAsCodepoints();
            HashMap<String, Long> roles = rolesList.get(messageID);
            updateIR();
            updateOpenTTD();
            updateRTM();
            updateTC();
            updateZnD();
            updateTTT();
            if (roles.containsKey(codepoints)) {
                event.getGuild().removeRoleFromMember(member, event.getGuild().getRoleById(roles.get(codepoints))).complete();
            }
        }
    }

    public static void checkIfReacted() {
        for (Long l : rolesList.keySet()) {
            Message m = Container.getGuild().getTextChannelById(channelID).retrieveMessageById(l).complete();
            Set<String> roles = rolesList.get(l).keySet();
            for (String s : roles) {
                m.addReaction(s).complete();
            }
        }
    }
}
