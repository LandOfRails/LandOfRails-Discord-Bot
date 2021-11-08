package commands.launcher;

import commands.interfaces.Command;
import model.Modpack;
import model.Triple;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import storage.Container;

import java.util.List;

import static commands.utils.LauncherModpackUtils.getModpackList;

public class CommandRemoveModpack implements Command {

    @Override
    public String getName() {
        return "removeModpack";
    }

    @Override
    public void onCall(MessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");

        MessageChannel channel = event.getChannel();

        if (!hasPermission(event.getMember())) {
            channel.sendMessage("**Du hast keine Berechtigung dazu!**").queue();
            return;
        }

        if (args.length != 2) {
            // @formatter:off
            String response =
                    "tt";
            // @formatter:on
            channel.sendMessage(response).queue();
        } else {

            List<Modpack> modpackList = getModpackList();

            for (Modpack m : modpackList) {
                if (m.getShortcut().equalsIgnoreCase(args[1])) {
                    event.getChannel().sendMessage("Bitte bestätige dass du das Modpack \"" + m.getTitle() + "\" wirklich löschen möchtest.").queue(message -> {
                        Container.modpackDeletionList.add(new Triple<>(message, event.getMember().getIdLong(), m));
                        message.addReaction("U+2705").queue();
                        message.addReaction("U+274C").queue();
                    });
                }
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
