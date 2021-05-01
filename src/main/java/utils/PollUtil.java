package utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;

public class PollUtil {

    /**
     * @param channel
     * @param author
     * @param question
     * @param description
     * @param voteOptions Options, Emojiunicode
     * @param ends
     */
    public static void startPoll(TextChannel channel, String author, String question, String description, TreeMap<String, String> voteOptions, Instant ends) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setFooter("Ends in");

        builder.setAuthor(author);
        builder.setTitle(question);
        builder.setDescription(description);

        for (Map.Entry<String, String> entry : voteOptions.entrySet()) {
            builder.addField(entry.getValue() + " " + entry.getKey(), null, false);
        }
    }

}
