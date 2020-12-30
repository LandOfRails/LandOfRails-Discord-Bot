package commands.launcher;

import org.apache.commons.lang.math.NumberUtils;

import commands.interfaces.Command;
import model.Modpack;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandAddModpack implements Command {

	@Override
	public String getName() {
		return "addModpack";
	}

	/**
	 * !addModpack name title mcVersion modpackversion organisation key
	 * locationonserver imageurl downloadedimage
	 */

	@Override
	public void onCall(MessageReceivedEvent event) {

		String[] args = event.getMessage().getContentRaw().split("\n");

		MessageChannel channel = event.getChannel();

		if (!hasPermission(event.getMember())) {
			channel.sendMessage("**Du hast keine Berechtigung dazu!**").queue();
			return;
		}

		if (args.length != 9) {
			// @formatter:off
			String response = 
					  "Falsche Syntax!"
					+ "!addModpack **Name**\n"
					+ "**Title**\n"
					+ "**MC Version**\n"
					+ "**Modpack Version**\n"
					+ "**Organisation**\n"
					+ "**Key**\n"
					+ "**Location on server**\n"
					+ "**Image URL**\n"
					+ "**Downloaded Image**";
			// @formatter:on
			channel.sendMessage(response).queue();
		} else {

			String name = args[0].split(" ")[1];
			String title = args[1];
			String mcVersion = args[2];
			String modpackVersion = args[3];
			String organisationText = args[4];
			String key = args[5];
			String locationOnServer = args[6];
			String imageUrl = args[7];
			String downloadedImage = args[8];

			Integer organisation = NumberUtils.toInt(organisationText, -1);
			if (organisation < 1) {
				String response = "\"" + organisationText + "\" ist keine gültige Zahl!";
				channel.sendMessage(response).queue();
			} else {
				channel.sendMessage("Erstellt!").queue();

				/** @formatter:off
				 * 
				 *  TODO:
				 *  Eingaben verifizieren
				 *  Modpack verarbeiten
				 *  
				 *  @formatter:on
				 */

				Modpack modpack = new Modpack(name, title, modpackVersion, mcVersion, organisation, key,
						locationOnServer, imageUrl, downloadedImage);
				channel.sendMessage(modpack.toString()).queue();
			}

		}

	}

	private boolean hasPermission(Member author) {
		if (author.isOwner())
			return true;

		for (Role r : author.getRoles()) {
			if (r.getName().equalsIgnoreCase("Management"))
				return true;
			if (r.getName().equalsIgnoreCase("Deputy Management"))
				return true;
		}

		return false;
	}

}
