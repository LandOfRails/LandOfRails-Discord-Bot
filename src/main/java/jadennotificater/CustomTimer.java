package jadennotificater;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import storage.Container;

public class CustomTimer {

	private CustomTimer() {

	}

	// Constants
	private static final String OWNER = "Owner";
	private static final long MANAGEMENT = 544172771404677120l;
	private static final LocalTime TIME = LocalTime.of(16, 0);

	// Timer
	private static Timer timer = new Timer();

	public static void init(String url, LocalDate date, long daysLeft) {

		TimerTask task = new TimerTask() {

			@Override
			public void run() {

				TextChannel management = Container.getGuild().getTextChannelById(MANAGEMENT);

				MessageBuilder msg = new MessageBuilder();

				Role owner = Container.getGuild().getRolesByName(OWNER, true).get(0);
				msg.append(owner);
				msg.append(" das Zertifikat für die URL ");
				msg.appendCodeLine(url);
				if (daysLeft > 0) {
					String days = daysLeft == 1 ? "einem Tag" : daysLeft + " Tagen";
					msg.append(" läuft in " + days + " ab!");
				} else if (daysLeft < 0) {
					msg.append(" ist abgelaufen!");
				} else {
					msg.append(" läuft heute ab!");
				}
				management.sendMessage(msg.build()).complete();

			}

		};

		Date time = fromLocalDate(date);
		timer.schedule(task, time);

	}

	private static Date fromLocalDate(LocalDate ld) {
		return java.sql.Timestamp.valueOf(LocalDateTime.of(ld, TIME));
	}

}
