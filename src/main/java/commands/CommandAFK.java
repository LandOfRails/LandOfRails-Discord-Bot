package commands;

import java.awt.Color;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import commands.interfaces.Aliases;
import commands.interfaces.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message.MentionType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandAFK implements Command, Aliases {

	// Speichert die UserIDs, aber nur zur Laufzeit
	protected static Map<String, LocalDateTime> afkPlayers = new HashMap<>();

	@Override
	public String getName() {
		return "afk";
	}

	@Override
	public String[] getAliases() {
		return new String[] { "afks" };
	}

	@Override
	public void onCall(MessageReceivedEvent event) {
		LocalDateTime now = LocalDateTime.now();
		String id = event.getAuthor().getId();
		MessageChannel channel = event.getChannel();
		String command = event.getMessage().getContentRaw();

		event.getMessage().delete().queueAfter(10, TimeUnit.SECONDS);

		if (command.startsWith("!afks")) {

			onCallAFKS(event, channel);

			return;
		}

		Member member = event.getMember();
		User user = member.getUser();

		if (afkPlayers.containsKey(id)) {
			afkPlayers.remove(id);

			EmbedBuilder builder = new EmbedBuilder();
			builder = builder.setColor(Color.GREEN);

			String avatar = user.getAvatarUrl();
			String title = member.getEffectiveName() + " is no longer AFK.";
			builder = builder.setAuthor(title, null, avatar);

			channel.sendMessage(builder.build()).queue(success -> success.delete().queueAfter(10, TimeUnit.SECONDS));

		} else {
			afkPlayers.put(id, now);

			EmbedBuilder builder = new EmbedBuilder();
			builder = builder.setColor(Color.RED);

			String avatar = user.getAvatarUrl();
			String title = member.getEffectiveName() + " is now AFK.";
			builder = builder.setAuthor(title, null, avatar);

			channel.sendMessage(builder.build()).queue(success -> success.delete().queueAfter(10, TimeUnit.SECONDS));
		}

	}

	public void onCallAFKS(MessageReceivedEvent event, MessageChannel channel) {
		EmbedBuilder builder = new EmbedBuilder();
		builder = builder.setColor(Color.ORANGE);
		builder = builder.setTitle("Players currently being AFK: " + afkPlayers.size());
		if (afkPlayers.isEmpty())
			builder = builder.appendDescription("None");
		channel.sendMessage(builder.build()).queue(success -> success.delete().queueAfter(10, TimeUnit.SECONDS));

		if (afkPlayers.size() > 0) {
			for (Entry<String, LocalDateTime> entry : afkPlayers.entrySet()) {
				Member m = event.getGuild().retrieveMemberById(entry.getKey()).complete();
				String name = m.getEffectiveName();
				LocalDateTime ldt = entry.getValue();
				String timespan = getTimespan(ldt);
				builder = builder.appendDescription(name + " (" + timespan + ")");

				String avatar = m.getUser().getAvatarUrl();
				String title = name + " (" + timespan + ")";

				// @formatter:off
				channel.sendMessage(
						new EmbedBuilder()
							.setColor(Color.RED)
							.setAuthor(title, null, avatar)
							.build()
						).queue(success -> success.delete().queueAfter(10, TimeUnit.SECONDS));
				// @formatter: on
			}
		}

	}

	public void onMessageEvent(MessageReceivedEvent event) {
		List<IMentionable> mentions = event.getMessage().getMentions(MentionType.USER);
		MessageChannel channel = event.getChannel();
		
		for (IMentionable mention : mentions) {
			String id = mention.getId();
			if(afkPlayers.containsKey(id) && !event.getAuthor().isBot()) {
				Member m = event.getGuild().retrieveMemberById(id).complete();
				
				String name = m.getEffectiveName();
				LocalDateTime ldt = afkPlayers.get(id);
				String timespan = getTimespan(ldt);

				String avatar = m.getUser().getAvatarUrl();
				String title = name + " is currently AFK. (" + timespan + ")";
				
				
				EmbedBuilder eb = new EmbedBuilder();
				eb = eb.setColor(Color.RED);
				eb = eb.setAuthor(title, null, avatar);
				
				event.getMessage().reply(eb.build()).queue(success -> success.delete().queueAfter(15, TimeUnit.SECONDS));
			}
		}
		
		// !afk und !afks
		boolean isAFKCommand = event.getMessage().getContentRaw().startsWith("!afk");
		if(!isAFKCommand && event.getMember() != null) {
			String id = event.getMember().getId();
			if(afkPlayers.containsKey(id)) {
				afkPlayers.remove(id);

				EmbedBuilder builder = new EmbedBuilder();
				builder = builder.setColor(Color.GREEN);
				Member member = event.getGuild().retrieveMemberById(id).complete();
				User user = member.getUser();

				String avatar = user.getAvatarUrl();
				String title = member.getEffectiveName() + " is no longer AFK.";
				builder = builder.setAuthor(title, null, avatar);

				channel.sendMessage(builder.build()).queue(success -> success.delete().queueAfter(10, TimeUnit.SECONDS));
			}
		}

	}

	/**
	 * 
	 * Gibt die höchsten zwei Zeiteinheiten zurück
	 * 1 day and 13 hours (1 Tag, 13 Stunden, X Minuten, X Sekunden)
	 * 12 hours and 1 minute (0 Tage, 12 Stunden, 1 Minute, X Sekunden)
	 * 43 minutes and 3 seconds (0 Tage, 0 Stunden, 43 Minuten, 3 Sekunden)
	 * 55 seconds (0 Tage, 0 Stunden, 0 Minuten, 55 Sekunden)
	 * 
	 * @param ldt
	 * @return
	 */
	@SuppressWarnings("java:S3776")
	public String getTimespan(LocalDateTime ldt) {
		Duration diff = Duration.between(LocalDateTime.now(), ldt).abs();

		long days = diff.toDays();
		long hours = diff.toHours();
		int minutes = (int) ((diff.getSeconds() % (60 * 60)) / 60);
		int seconds = (int) (diff.getSeconds() % 60);

		String ret = "";

		if (days > 0) {
			ret += days;
			ret += days > 1 ? " days and " : " day and ";
			ret += hours;
			ret += hours > 1 ? " hours" : " hour";
		} else if (hours > 0) {
			ret += hours;
			ret += hours > 1 ? " hours and " : " hour and ";
			ret += minutes;
			ret += minutes > 1 ? " minutes" : " minute";
		} else if (minutes > 0) {
			ret += minutes;
			ret += minutes > 1 ? " minutes and " : " minute and ";
			ret += seconds;
			ret += seconds > 1 ? " seconds" : " second";
		} else {
			ret += seconds;
			ret += seconds > 1 ? " seconds" : " second";
		}

		return ret;
	}

}
