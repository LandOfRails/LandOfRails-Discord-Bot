package commands;

import commands.interfaces.Command;
import model.Pair;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import utils.PollUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class CommandAbstimmungNeu implements Command {

    @Override
    public String getName() {
        return "abstimmungneu";
    }

    @Override
    public void onCall(MessageReceivedEvent event) {

        String messageTextRaw = event.getMessage().getContentRaw();

        for (Role r : event.getMember().getRoles()) {
            try {
                if (r.getId().equals("542380089124323359") || r.getId().equals("438074536508784640")
                        || r.getId().equals("456916096587530241") || r.getId().equals("514172638717935635")
                        || r.getId().equals("709848394725851211") || r.getId().equals("529727596942983187")) {
                    event.getMessage().delete().queue();
                    String[] args = messageTextRaw.split(" ", 3);

                    String question = "";
                    Date date = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    if (isNumeric(args[1])) {
                        int timer = Integer.parseInt(args[2]);
                        calendar.add(Calendar.HOUR_OF_DAY, timer);
                        date = calendar.getTime();
                        question = args[2];
                    } else {
                        calendar.add(Calendar.HOUR_OF_DAY, 48);
                        date = calendar.getTime();
                        args = messageTextRaw.split(" ", 2);
                        question = args[1];
                    }

                    TreeMap<String, Pair<String, String>> options = new TreeMap<>();
                    options.put("Ja!", new Pair<>("U+1f44d", ":thumbsup:"));
                    options.put("Ja!", new Pair<>("U+1f44e", ":thumbsdown:"));
                    options.put("Ja!", new Pair<>("U+270a", ":fist:"));

                    PollUtil.startPoll(event.getGuild().getTextChannelById(581602258102517760L), event.getMember(), question, "FUTURE DESCRIPTION", options, date, (byte) 0);

                    break;
                }
            } catch (Exception e) {
                event.getChannel().sendMessage(e.getLocalizedMessage()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
            }
        }
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
