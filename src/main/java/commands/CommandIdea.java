package commands;

import javax.annotation.Nonnull;

import commands.interfaces.Command;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandIdea implements Command {

	@Override
	public String getName() {
		return "idea";
	}

	@Override
	public void onCall(@Nonnull MessageReceivedEvent event) {

		MessageChannel channel = event.getChannel();
		String messageTextRaw = event.getMessage().getContentRaw();

		String[] args = messageTextRaw.split(" ", 3);
		if (args.length < 3) {
			channel.sendMessage("__Please enter a server.__ \n"
					+ "If you have a general suggestion choose \"all\", if the suggestion applies to a specific server choose between \"tc\", \"znd\", \"ir\" or \"rtm\". For example, the command should look like this: \"!idea **all** *Idea*\".")
					.queue();
		} else {
			switch (args[1].toLowerCase()) {
			case "all":
				event.getGuild().getTextChannelById(625701271051042905L)
						.sendMessage("**Idee von:** " + event.getAuthor().getAsMention() + "\n \n" + args[2])
						.queue(message -> {
							message.addReaction(event.getGuild().getEmoteById(640284218974273546L)).queue();
							message.addReaction(event.getGuild().getEmoteById(640284247084498965L)).queue();
						});
				event.getChannel().sendMessage("Thank you very much for your suggestion "
						+ event.getAuthor().getAsMention() + "! It was successfully forwarded to the entire team.")
						.queue();
				break;
			case "tc":
				event.getGuild().getTextChannelById(518535634923814936L)
						.sendMessage("**Idee von:** " + event.getAuthor().getAsMention() + "\n \n" + args[2])
						.queue(message -> {
							message.addReaction(event.getGuild().getEmoteById(640284218974273546L)).queue();
							message.addReaction(event.getGuild().getEmoteById(640284247084498965L)).queue();
						});
				event.getChannel()
						.sendMessage("Thank you very much for your suggestion " + event.getAuthor().getAsMention()
								+ "! It was successfully forwarded to the entire team of Traincraft.")
						.queue();
				break;
			case "znd":
				event.getGuild().getTextChannelById(709848903540801586L)
						.sendMessage("**Idee von:** " + event.getAuthor().getAsMention() + "\n \n" + args[2])
						.queue(message -> {
							message.addReaction(event.getGuild().getEmoteById(640284218974273546L)).queue();
							message.addReaction(event.getGuild().getEmoteById(640284247084498965L)).queue();
						});
				event.getChannel()
						.sendMessage("Thank you very much for your suggestion " + event.getAuthor().getAsMention()
								+ "! It was successfully forwarded to the entire team of Zora no Densha.")
						.queue();
				break;
			case "ir":
				event.getGuild().getTextChannelById(530331487241764884L)
						.sendMessage("**Idee von:** " + event.getAuthor().getAsMention() + "\n \n" + args[2])
						.queue(message -> {
							message.addReaction(event.getGuild().getEmoteById(640284218974273546L)).queue();
							message.addReaction(event.getGuild().getEmoteById(640284247084498965L)).queue();
						});
				event.getChannel()
						.sendMessage("Thank you very much for your suggestion " + event.getAuthor().getAsMention()
								+ "! It was successfully forwarded to the entire team of Immersive Railroading.")
						.queue();
				break;
			case "rtm":
				event.getGuild().getTextChannelById(530331723238473728L)
						.sendMessage("**Idee von:** " + event.getAuthor().getAsMention() + "\n \n" + args[2])
						.queue(message -> {
							message.addReaction(event.getGuild().getEmoteById(640284218974273546L)).queue();
							message.addReaction(event.getGuild().getEmoteById(640284247084498965L)).queue();
						});
				event.getChannel()
						.sendMessage("Thank you very much for your suggestion " + event.getAuthor().getAsMention()
								+ "! It was successfully forwarded to the entire team of Real Train Mod.")
						.queue();
				break;
			}
		}

	}

}
