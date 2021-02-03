package handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.ServerState;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import storage.Container;

import java.awt.*;
import java.io.File;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.*;
import java.util.logging.Logger;

public class TimerTasks {

    private static final Logger logger = Logger.getLogger("TimerTask");

    public void checkActiveVotings() {
        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run() {
                TextChannel textChannel = Container.getGuild().getTextChannelById(485879824540499969L);
                for (int i = 0; i < Container.ActiveVotings.size(); i++) {
                    Long id = Container.ActiveVotings.get(i);
                    logger.info("Nachricht (" + id + ") wird ¸berpr¸ft...");
                    try {
                        Message m = textChannel.retrieveMessageById(id).complete();
                        OffsetDateTime odt = null;
                        for (MessageEmbed me : m.getEmbeds()) {
                            odt = me.getTimestamp();
                        }
                        if (ChronoUnit.SECONDS.between(odt.toInstant(), Instant.now()) > 1) {
                            // Abstimmung schlie√üen
                            for (MessageEmbed me : m.getEmbeds()) {
                                EmbedBuilder embedBuilder = new EmbedBuilder();
                                embedBuilder.setFooter("Abstimmung beendet.");
                                embedBuilder.setTitle(me.getTitle());
                                embedBuilder.setAuthor(me.getAuthor().getName());

                                // Count reactions
                                int pos = 0;
                                int neg = 0;
                                int idk = 0;
                                for (MessageReaction mr : m.getReactions()) {
                                    if (mr.getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+1F44D")) {
                                        pos = mr.getCount() - 1;
                                    } else if (mr.getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+1F44E")) {
                                        neg = mr.getCount() - 1;
                                    } else if (mr.getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+270A")) {
                                        idk = mr.getCount() - 1;
                                    }
                                }
                                if ((pos + neg + idk) >= 15) {
                                    // Check which color
                                    if (pos > neg) {
                                        embedBuilder.setColor(Color.GREEN);
                                    } else {
                                        embedBuilder.setColor(Color.RED);
                                    }

                                    embedBuilder.setDescription("\uD83D\uDC4D Ja! = **" + (pos)
                                            + "** \n \n \uD83D\uDC4E Nein! = **" + (neg)
                                            + "** \n \n :fist: Mir egal... = **" + (idk) + "**");
                                    m.editMessage(embedBuilder.build()).queue();
                                    m.clearReactions().complete();

                                    // Nachricht aus Liste entfernen
                                    logger.info("Abstimmung (" + id + ") beendet.");
                                    Container.ActiveVotings.remove(i);
                                    i--;
                                }
                            }
                        }
                    } catch (ErrorResponseException e) {
                        logger.severe("Nachricht nicht mehr gefunden. \n " + id + " wird aus der Liste gel√∂scht...");
                        Container.ActiveVotings.remove(i);
                        i--;
                    }
                }
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 1000L;
        long period = 1000L * 60L * 10L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

    private String stateTC = "null";
    private String stateIR = "null";
    private String stateZND = "null";
    private String stateRTM = "null";

    public void updateStatus() {

        TimerTask repeatedTask = new TimerTask() {

            @Override
            public void run() {

                List<ServerState> states = getStatesList();
                for (ServerState state : states) {
                    String tempState = state.getState();
                    switch (state.getUuid()) {

                        //IR
                        case "8b2512df-5763-4ef8-9886-133bc8a11095":
                            if (!stateIR.equals(tempState)) {
                                stateIR = tempState;
                                Container.getGuild().getVoiceChannelById(806319545279053844L).getManager().setName("IR: " + tempState).queue();
                            }
                            break;

                        //TC
                        case "d7f261eb-22a4-4b8e-b763-4f452a646157":
                            if (!stateTC.equals(tempState)) {
                                stateTC = tempState;
                                Container.getGuild().getVoiceChannelById(806319039831605269L).getManager().setName("TC: " + tempState).queue();
                            }
                            break;

                        //ZnD
                        case "0996687f-22f0-4946-addc-ba9e46cb2bd0":
                            if (!stateZND.equals(tempState)) {
                                stateZND = tempState;
                                Container.getGuild().getVoiceChannelById(806319634998362142L).getManager().setName("ZnD: team only (" + tempState + ")").queue();
                            }
                            break;

                        //RTM
                        case "0f67780a-465c-4139-9e51-17a4639ddbc0":
                            if (!stateRTM.equals(tempState)) {
                                stateRTM = tempState;
                                Container.getGuild().getVoiceChannelById(806319668344127538L).getManager().setName("RTM: team only (" + tempState + ")").queue();
                            }
                            break;

                        default:
                            break;
                    }
                }

            }
        };
        Timer timer = new Timer("Timer");

        long delay = 1000L;
        long period = 1000L * 10L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

    private List<ServerState> getStatesList() {
        String json = "";
        File jsonFile = new File("/var/lib/pterodactyl/states.json");
        try {
            Scanner s = new Scanner(jsonFile);
            while (s.hasNextLine()) {
                json += s.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<ServerState> serverStates = new ArrayList<>();

        JsonElement element = JsonParser.parseString(json);
        JsonObject obj = element.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            serverStates.add(new ServerState(entry.getKey(), entry.getValue().getAsString()));
        }
        return serverStates;
    }
}
