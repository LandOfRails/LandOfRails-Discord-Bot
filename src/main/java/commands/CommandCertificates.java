package commands;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Map.Entry;

import commands.interfaces.Aliases;
import commands.interfaces.Command;
import jadennotificater.JadenNotificater;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandCertificates implements Command, Aliases {

	@Override
	public String[] getAliases() {
		return new String[] { "certificate", "cert", "certs", "zertifikate", "zertifikat" };
	}

	@Override
	public String getName() {
		return "certificates";
	}

	@Override
	public void onCall(MessageReceivedEvent event) {

		MessageChannel channel = event.getChannel();
		Map<String, LocalDate> certs = JadenNotificater.getCertificateDates();

		MessageBuilder builder = new MessageBuilder();

		builder.append("**Zertifikate:**\n");

		for (Entry<String, LocalDate> entry : certs.entrySet()) {
			String url = entry.getKey();
			LocalDate notAfter = entry.getValue();
			long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), notAfter);

			builder.append("Das Zertifikat für die URL ");
			builder.appendCodeLine(url);
			if (daysLeft < 0) {
				builder.append(" ist bereits abgelaufen.");
			} else if (daysLeft == 0) {
				builder.append(" läuft heute ab!");
			} else {
				String days = daysLeft == 1 ? "einem Tag" : daysLeft + " Tagen";
				builder.append(" läuft in " + days + " ab.");
			}
			builder.append("\n");

		}

		channel.sendMessage(builder.build()).complete();

	}

}
