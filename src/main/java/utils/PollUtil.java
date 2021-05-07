package utils;

import model.Pair;
import model.database.PollOptionsEntity;
import model.database.PollsEntity;
import model.database.UsersEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.hibernate.Session;

import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class PollUtil {

    /**
     * @param channel
     * @param author
     * @param question
     * @param description
     * @param voteOptions Options, Pair<\Unicode, :EMOJI:>
     * @param ends
     */
    public static void startPoll(TextChannel channel, Member author, String question, String description, TreeMap<String, Pair<String, String>> voteOptions, java.util.Date ends, byte teamVoting) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setFooter("Ends in");

        builder.setAuthor(author.getNickname());
        builder.setTitle(question);
        builder.setDescription(description);

        for (Map.Entry<String, Pair<String, String>> entry : voteOptions.entrySet()) {
            builder.addField(entry.getValue().getRight() + " " + entry.getKey(), "", false);
        }
        builder.setTimestamp(ends.toInstant());
        builder.setColor(Color.YELLOW);

        Session session = HibernateHelper.sessionFactory.openSession();
        session.beginTransaction();
        PollsEntity pe = new PollsEntity();
        pe.setUsersByMemberId((UsersEntity) session.createQuery("from UsersEntity where memberId=" + author.getIdLong()).list().get(0));
        pe.setQuestion(question);
        pe.setEndDatetime(new Timestamp(ends.getTime()));
        pe.setTeamVoting(teamVoting);
        Collection<PollOptionsEntity> pollOptionsEntities = new ArrayList<>();
        for (Map.Entry<String, Pair<String, String>> entry : voteOptions.entrySet()) {
            PollOptionsEntity poe = new PollOptionsEntity();
            poe.setPollsByFkPollId(pe);
            poe.setVoteOption(entry.getKey());
            poe.setVotes(0);
            pollOptionsEntities.add(poe);
            session.save(poe);
        }
        pe.setPollOptionsById(pollOptionsEntities);
        session.save(pe);
        session.getTransaction().commit();
        session.close();

        channel.sendMessage(builder.build()).queue(message -> {
            for (Pair<String, String> value : voteOptions.values()) message.addReaction(value.getLeft()).complete();
        });
    }

}
