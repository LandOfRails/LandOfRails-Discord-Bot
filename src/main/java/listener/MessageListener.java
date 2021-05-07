package listener;

import commands.handler.CommandList;
import commands.interfaces.Aliases;
import commands.interfaces.Command;
import model.database.UsersEntity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.hibernate.Session;
import storage.Container;
import utils.HibernateHelper;

import javax.annotation.Nonnull;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MessageListener extends ListenerAdapter {

    // private static final Logger logger = Logger.getLogger("MessageListener");
    private CommandList commandList = new CommandList();
    public static final String COMMANDPREFIX = "!";

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        Session session = HibernateHelper.sessionFactory.openSession();
        session.beginTransaction();
        List<UsersEntity> result = session.createQuery("from  UsersEntity").list();
        boolean memberFound = false;
        for (UsersEntity ue : result) {
            if (ue.getMemberId() == event.getMember().getIdLong()) {
                memberFound = true;
                ue.setMessageCount(ue.getMessageCount() + 1);
                break;
            }
        }
        if (!memberFound) {
            UsersEntity usersEntity = new UsersEntity();
            usersEntity.setMemberId(event.getMember().getIdLong());
            usersEntity.setDiscordName(event.getMember().getEffectiveName());
            usersEntity.setMessageCount(1);
            session.save(usersEntity);
        }
        session.getTransaction().commit();
        session.close();

        String messageTextRaw = event.getMessage().getContentRaw();

        if (event.getChannel().getId().equals("485879824540499969") && !event.getAuthor().isBot()
                && !event.getMessage().getContentRaw().contains("abstimmung")) {
            event.getMessage().delete().queue();
            event.getChannel()
                    .sendMessage("Bitte in " + event.getGuild().getTextChannelById(394116521447915521L).getAsMention()
                            + " \u00fcber die aktuellen Abstimmungen diskutieren. Danke :)")
                    .complete().delete().queueAfter(10, TimeUnit.SECONDS);
        }

        // For !afk command
        CommandList.AFKCOMMAND.onMessageEvent(event);

        // Überprüfen ob Befehl
        if (messageTextRaw.startsWith(COMMANDPREFIX)) {

            String cmd = messageTextRaw.substring(1).split("\n")[0];
            boolean called = false;
            for (Command c : commandList.getCommands()) {
                if (cmd.matches("^" + c.getName() + "(\\s.*|$)")) {
                    c.onCall(event);
                    called = true;
                } else if (c instanceof Aliases) {
                    Aliases a = (Aliases) c;
                    if (startsWith(cmd, a.getAliases())) {
                        c.onCall(event);
                        called = true;
                    }
                }

            }

            if (!called && !messageTextRaw.equals("!")) {

                Connection conn = Container.getConnection();
                try {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM commandIdeas WHERE command='" + event.getMessage().getContentRaw() + "'");
                    rs.last();
                    DateFormat dtf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                    if (rs.getRow() == 0) {
                        Timestamp time = new Timestamp(System.currentTimeMillis());
                        PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO commandIdeas (MemberID, MemberUsername, Command, Timestamp, JumpUrl, Count) VALUES (" + event.getMember().getIdLong() + ", '" + event.getMember().getEffectiveName() + "', '" + event.getMessage().getContentRaw() + "', ?, '" + event.getMessage().getJumpUrl() + "', " + 0 + ")");
                        stmt2.setTimestamp(1, time);
                        stmt2.execute();
                        event.getChannel().sendMessage(
                                "This command does not exist yet. It has been added to the todo list and may be included at some point :)")
                                .queue();
                        stmt2.close();
                    } else {
                        Statement stmt3 = conn.createStatement();
                        stmt3.execute("UPDATE commandIdeas SET Count = Count + 1 WHERE Command='" + event.getMessage().getContentRaw() + "'");
                        stmt3.close();
                        event.getChannel().sendMessage("The command " + rs.getString("Command") + " was already suggested by " + rs.getString("MemberUsername") + " on " + dtf.format(rs.getDate("Timestamp")) + ". (" + rs.getString("JumpUrl") + ") Maybe it will be implemented sometime :)").queue();
                    }
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    try {
                        conn.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }
    }

    private boolean startsWith(String target, String[] array) {
        for (String s : array) {
            if (target.matches("^" + s + "(\\s.*|$)"))
                return true;
        }
        return false;
    }

}
