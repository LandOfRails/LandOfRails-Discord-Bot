package listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import storage.Container;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        String messageTextRaw = event.getMessage().getContentRaw();

        if (event.getChannel().getId().equals("485879824540499969") && !event.getAuthor().isBot() && !event.getMessage().getContentRaw().contains("abstimmung")) {
            event.getMessage().delete().queue();
            event.getChannel().sendMessage("Bitte in " + event.getGuild().getTextChannelById(394116521447915521L).getAsMention() + " \u00fcber die aktuellen Abstimmungen diskutieren. Danke :)").complete().delete().queueAfter(10, TimeUnit.SECONDS);
        }

        //Überprüfen ob Befehl
        if (messageTextRaw.startsWith("!")) {
            MessageChannel channel = event.getChannel();
            TextChannel textChannel = event.getTextChannel();
            Member member = event.getMember();


            //Idea
            if (messageTextRaw.contains("idea")) {
                String[] args = messageTextRaw.split(" ", 3);
                if (args.length < 3) {
                    channel.sendMessage("__Please enter a server.__ \n" +
                            "If you have a general suggestion choose \"all\", if the suggestion applies to a specific server choose between \"tc\", \"znd\", \"ir\" or \"rtm\". For example, the command should look like this: \"!idea **all** *Idea*\".").queue();
                } else {
                    switch (args[1].toLowerCase()) {
                        case "all":
                            event.getGuild().getTextChannelById(625701271051042905L).sendMessage("**Idee von:** " + event.getAuthor().getAsMention() + "\n \n" +
                                    args[2]).queue(message -> {
                                message.addReaction(event.getGuild().getEmoteById(640284218974273546L)).queue();
                                message.addReaction(event.getGuild().getEmoteById(640284247084498965L)).queue();
                            });
                            event.getChannel().sendMessage("Thank you very much for your suggestion " + event.getAuthor().getAsMention() + "! It was successfully forwarded to the entire team.").queue();
                            break;
                        case "tc":
                            event.getGuild().getTextChannelById(518535634923814936L).sendMessage("**Idee von:** " + event.getAuthor().getAsMention() + "\n \n" +
                                    args[2]).queue(message -> {
                                message.addReaction(event.getGuild().getEmoteById(640284218974273546L)).queue();
                                message.addReaction(event.getGuild().getEmoteById(640284247084498965L)).queue();
                            });
                            event.getChannel().sendMessage("Thank you very much for your suggestion " + event.getAuthor().getAsMention() + "! It was successfully forwarded to the entire team of Traincraft.").queue();
                            break;
                        case "znd":
                            event.getGuild().getTextChannelById(709848903540801586L).sendMessage("**Idee von:** " + event.getAuthor().getAsMention() + "\n \n" +
                                    args[2]).queue(message -> {
                                message.addReaction(event.getGuild().getEmoteById(640284218974273546L)).queue();
                                message.addReaction(event.getGuild().getEmoteById(640284247084498965L)).queue();
                            });
                            event.getChannel().sendMessage("Thank you very much for your suggestion " + event.getAuthor().getAsMention() + "! It was successfully forwarded to the entire team of Zora no Densha.").queue();
                            break;
                        case "ir":
                            event.getGuild().getTextChannelById(530331487241764884L).sendMessage("**Idee von:** " + event.getAuthor().getAsMention() + "\n \n" +
                                    args[2]).queue(message -> {
                                message.addReaction(event.getGuild().getEmoteById(640284218974273546L)).queue();
                                message.addReaction(event.getGuild().getEmoteById(640284247084498965L)).queue();
                            });
                            event.getChannel().sendMessage("Thank you very much for your suggestion " + event.getAuthor().getAsMention() + "! It was successfully forwarded to the entire team of Immersive Railroading.").queue();
                            break;
                        case "rtm":
                            event.getGuild().getTextChannelById(530331723238473728L).sendMessage("**Idee von:** " + event.getAuthor().getAsMention() + "\n \n" +
                                    args[2]).queue(message -> {
                                message.addReaction(event.getGuild().getEmoteById(640284218974273546L)).queue();
                                message.addReaction(event.getGuild().getEmoteById(640284247084498965L)).queue();
                            });
                            event.getChannel().sendMessage("Thank you very much for your suggestion " + event.getAuthor().getAsMention() + "! It was successfully forwarded to the entire team of Real Train Mod.").queue();
                            break;
                    }
                }
            }

            //Remove
            else if (messageTextRaw.contains("remove")) {
                boolean isAllowed = false;
                for (Role r : member.getRoles()) {
                    if (r.getId().equals("394112698511654912")) {
                        isAllowed = true;
                    }
                }
                if (isAllowed) {
                    String[] args = messageTextRaw.split(" ");
                    if (args.length == 1) {
                        channel.sendMessage("Gib eine Anzahl an zu l\u00f6schenden Nachrichten an. (!remove 5)").queue();
                    } else {
                        event.getMessage().delete().complete();
                        MessageHistory mh = new MessageHistory(textChannel);
                        textChannel.deleteMessages(mh.retrievePast(Integer.parseInt(args[1])).complete()).queue();
                    }
                } else {
                    channel.sendMessage("Du hast keine Berechtigung f\u00fcr diesen Befehl.").queue();
                }
            }

            //Abstimmung
            else if (messageTextRaw.contains("abstimmung")) {
                for (Role r : event.getMember().getRoles()) {
                    if (r.getId().equals("542380089124323359") || r.getId().equals("438074536508784640") || r.getId().equals("456916096587530241") || r.getId().equals("514172638717935635") || r.getId().equals("709848394725851211") || r.getId().equals("529727596942983187")) {
                        event.getMessage().delete().queue();
                        String[] args = messageTextRaw.split(" ", 4);

                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setFooter("Abstimmung endet");
                        Date date = new Date();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);

                        if (isNumeric(args[2])) {
                            int timer = Integer.parseInt(args[2]);

                            calendar.add(Calendar.HOUR_OF_DAY, timer);
                            date = calendar.getTime();

                            embedBuilder.setTimestamp(date.toInstant());
                            embedBuilder.setTitle(args[3]);
                        } else {
                            calendar.add(Calendar.HOUR_OF_DAY, 48);
                            date = calendar.getTime();

                            embedBuilder.setTimestamp(date.toInstant());
                            args = messageTextRaw.split(" ", 3);
                            embedBuilder.setTitle(args[2]);
                        }

                        embedBuilder.setAuthor(args[1]);
                        embedBuilder.setColor(Color.YELLOW);
                        embedBuilder.setDescription("\uD83D\uDC4D Ja! \n \n \uD83D\uDC4E Nein! \n \n :fist: Mir egal...");

                        event.getGuild().getTextChannelById(485879824540499969L).sendMessage(embedBuilder.build()).queue(message -> {
                            message.addReaction("U+1F44D").queue();
                            message.addReaction("U+1F44E").queue();
                            message.addReaction("U+270A").queue();
                            Container.ActiveVotings.add(message.getIdLong());
                        });
                        break;
                    }
                }
            }

            //AFK
            else if (messageTextRaw.contains("!afk")) {

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDateTime now = LocalDateTime.now();

                event.getChannel().sendMessage("Kommt noch...");
                //TODO
            }

            //Info
            else if (messageTextRaw.contains("!info") || messageTextRaw.contains("!landofrails") || messageTextRaw.contains("!lor")) {
                event.getChannel().sendMessage("Wir sind LandOfRails! (Info kommt noch...)");
            }

            //Stop
            else if (messageTextRaw.contains("stop")) {
                boolean isAllowed = false;
                for (Role r : member.getRoles()) {
                    if (r.getId().equals("394112698511654912")) {
                        isAllowed = true;
                    }
                }
                if (isAllowed) {
                    event.getJDA().shutdown();
                    System.out.println("Der Bot wurde gestoppt.");
                } else {
                    channel.sendMessage("Du hast keine Berechtigung f\u00fcr diesen Befehl.").queue();
                }
            } else if (messageTextRaw.contains("!commands") || messageTextRaw.contains("!help") || messageTextRaw.contains("!hilfe")) {
                boolean isOwner = false;
                boolean canStartPoll = false;
                for (Role r : member.getRoles()) {
                    if (r.getId().equals("394112698511654912")) {
                        isOwner = true;
                    }
                    if (r.getId().equals("542380089124323359") || r.getId().equals("438074536508784640") || r.getId().equals("456916096587530241") || r.getId().equals("514172638717935635") || r.getId().equals("709848394725851211") || r.getId().equals("529727596942983187")) {
                        canStartPoll = true;
                    }
                }

                final EmbedBuilder helpEmbed = new EmbedBuilder();
                helpEmbed.setColor(Color.RED);
                helpEmbed.setTitle("LandOfRails Help page");
                helpEmbed.setDescription("All commands of the LandOfRails discord bot.");
                helpEmbed.addBlankField(false);
                helpEmbed.addField("General commands:", "", false);
                helpEmbed.addField("!idea [all|tc|znd|ir|rtm] <message>", "Submit an idea for our servers :)", true);
                helpEmbed.addField("!afk", "Work in progress", true);
                helpEmbed.addField("!info", "About the LandOfRails Server (WIP)", true);
                helpEmbed.addField("!help", "This help page :)", true);
                helpEmbed.addField("Fun Commands:", "", false);
                helpEmbed.addField("!fish", "Fish GIF", true);
                helpEmbed.addField("!bee", "Minecraft bee", true);
                helpEmbed.addField("!duck", "Duck GIF", true);
                helpEmbed.addField("!train", "Railroad GIF", true);
                helpEmbed.addField("!tram", "One of 3 Tram GIF's", true);
                helpEmbed.addField("!cat[2]", "Cat GIF", true);
                helpEmbed.addField("!fuck", "Cursed.", true);

                if (isOwner) {
                    helpEmbed.addBlankField(false);
                    helpEmbed.addField("Owner Commands:", "", false);
                    helpEmbed.addField("!remove [anzahl]", "L\u00f6sche [anzahl] Nachrichten", true);
                    helpEmbed.addField("!stop", "Stoppe den Bot", true);
                }

                /*if(canStartPoll) {
                                helpEmbed.addBlankField(false);
                    helpEmbed.addField("Poll Command(s):", "", false);
                    helpEmbed.addField("abstimmung", "Keine ahnung wie der krams geht lul", true); // @MarkenJaden Musst du machen
                }*/

                channel.sendMessage(helpEmbed.build()).queue();
            } else {
                //Fun Commands
                switch (messageTextRaw) {
                    case "!fish":
                        event.getGuild().getTextChannelById(532648338391040031L).sendMessage("https://media.discordapp.net/attachments/625373674286874665/628653269790490665/618747043854155776.gif").queue();
                        break;
                    case "!bee":
                        event.getGuild().getTextChannelById(532648338391040031L).sendMessage("https://media.discordapp.net/attachments/494702234744127498/634442208291979285/aaaaaaaaaaa.gif").queue();
                        break;
                    case "!duck":
                        event.getGuild().getTextChannelById(532648338391040031L).sendMessage("https://i.gifer.com/XOsX.gif").queue();
                        break;
                    case "!train":
                        event.getGuild().getTextChannelById(532648338391040031L).sendMessage("https://cdn.discordapp.com/attachments/314814038662053888/712807142931038239/ezgif.com-video-to-gif_1.gif").queue();
                        break;
                    case "!tram":
                        switch (ThreadLocalRandom.current().nextInt(1, 3 + 1)) {
                            case 1:
                                event.getGuild().getTextChannelById(532648338391040031L).sendMessage("https://media1.tenor.com/images/6ae61031c768f8e2980908bd1a67b850/tenor.gif?itemid=14228705").queue();
                                break;
                            case 2:
                                event.getGuild().getTextChannelById(532648338391040031L).sendMessage("https://media.giphy.com/media/Tl2u4yXA90vQs/giphy.gif").queue();
                                break;
                            case 3:
                                event.getGuild().getTextChannelById(532648338391040031L).sendMessage("https://media.giphy.com/media/80QxoQtZb4Gli/giphy.gif").queue();
                                break;
                            default:
                                event.getChannel().sendMessage("Error Random Tram").queue();
                                break;
                        }
                        break;
                    case "!cat":
                        event.getGuild().getTextChannelById(532648338391040031L).sendMessage("https://media.discordapp.net/attachments/669158942139613186/740009211391377432/45452.gif").queue();
                        break;
                    case "!cat2":
                        event.getGuild().getTextChannelById(532648338391040031L).sendMessage("https://media.discordapp.net/attachments/730561686590717964/740596417390706788/5f2ac03410bd0496934562.gif").queue();
                        break;
                    case "!fuck":
                        event.getGuild().getTextChannelById(532648338391040031L).sendMessage("https://tenor.com/view/wtf-haha-flirty-fuck-smile-gif-15931510").queue();
                        break;
                    default:
                        //TODO Implement Databases
                        if (!Container.CommandIdeas.exists()) {
                            try {
                                Container.CommandIdeas.createNewFile();
                            } catch (final IOException e) {
                                e.printStackTrace();
                            }
                        }
                        try (FileWriter fw = new FileWriter(Container.CommandIdeas, true);
                             BufferedWriter bw = new BufferedWriter(fw);
                             PrintWriter out = new PrintWriter(bw)) {
                            out.println(messageTextRaw);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        event.getChannel().sendMessage("Diesen Befehl gibt es noch nicht. Er wurde zur Liste hinzugef\u00fcgt und wird vielelicht irgendwann mit eingebaut :)").queue();
                        break;
                }
            }

            //Restart
//            if (messageTextRaw.contains("restart")) {
//                boolean isAllowed = false;
//                for (Role r : member.getRoles()) {
//                    if (r.getId().equals("394112698511654912")) {
//                        isAllowed = true;
//                    }
//                }
//                if (isAllowed) {
//                    event.getJDA().shutdown();
//                } else {
//                    channel.sendMessage("Du hast keine Berechtigung f\u00fcr diesen Befehl.").queue();
//                }
//            }

        }
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
