package handler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import storage.Container;

import java.awt.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTasks {

    public void checkActiveVotings() {
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                TextChannel textChannel = Container.guild.getTextChannelById(485879824540499969L);
                for (int i = 0; i < Container.ActiveVotings.size(); i++) {
                    Long id = Container.ActiveVotings.get(i);
                    System.out.println("Nachricht (" + id + ") wird überprüft...");
                    try {
                        Message m = textChannel.retrieveMessageById(id).complete();
                        if (ChronoUnit.SECONDS.between(m.getTimeCreated().toInstant(), Instant.now()) > 1) {
                            //Abstimmung schließen
                            for (MessageEmbed me : m.getEmbeds()) {
                                EmbedBuilder embedBuilder = new EmbedBuilder();
                                embedBuilder.setFooter("Abstimmung beendet.");
                                embedBuilder.setTitle(me.getTitle());
                                embedBuilder.setAuthor(me.getAuthor().getName());

                                //Count reactions
                                int pos = 0;
                                int neg = 0;
                                int idk = 0;
                                for (MessageReaction mr : m.getReactions()) {
                                    if (mr.getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+1F44D")) {
                                        pos = mr.getCount();
                                    } else if (mr.getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+1F44E")) {
                                        neg = mr.getCount();
                                    } else if (mr.getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+270A")) {
                                        idk = mr.getCount();
                                    }
                                }
                                if ((pos + neg + idk) >= 15) {
                                    //Check which color
                                    if (pos > neg) {
                                        embedBuilder.setColor(Color.GREEN);
                                    } else {
                                        embedBuilder.setColor(Color.RED);
                                    }

                                    embedBuilder.setDescription("\uD83D\uDC4D Ja! = **" + (pos - 1) + "** \n \n \uD83D\uDC4E Nein! = **" + (neg - 1) + "** \n \n :fist: Mir egal... = **" + (idk - 1) + "**");
                                    m.editMessage(embedBuilder.build()).queue();
                                    m.clearReactions().complete();

                                    //Nachricht aus Liste entfernen
                                    System.out.println("Abstimmung (" + id + ") beendet.");
                                    Container.ActiveVotings.remove(i);
                                    i = i - 1;
                                }
                            }
                        }
                    } catch (ErrorResponseException e) {
                        System.out.println("Nachricht nicht mehr gefunden. \n " + id + " wird aus der Liste gelöscht...");
                        Container.ActiveVotings.remove(i);
                        i = i - 1;
                    }
                }
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 1000L;
        long period = 1000L * 60L * 10L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

}
