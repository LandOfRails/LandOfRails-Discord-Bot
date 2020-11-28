package commands.launcher;

import commands.interfaces.Command;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import storage.Container;

import java.util.List;

public class CommandRemovePermission implements Command {
    @Override
    public String getName() {
        return "removePermission";
    }

    @Override
    public void onCall(MessageReceivedEvent event) {
        User author = event.getAuthor();

        String[] args = event.getMessage().getContentRaw().split(" ", 3);
        if (args.length < 3) {
            event.getChannel().sendMessage("Please enter a valid server shortcut.").queue();
        } else {
            switch (args[1].toLowerCase()) {
                case "tc":
                    if (IsAllowed(Container.LauncherPermissionListTC, author)) {
                        Container.LauncherPermissionListTC.remove(event.getMessage().getMentionedUsers().get(0).getIdLong());
                        event.getChannel().sendMessage("Permission revoked.").queue();
                    }
                    break;
                case "ir":
                    if (IsAllowed(Container.LauncherPermissionListIR, author)) {
                        Container.LauncherPermissionListIR.remove(event.getMessage().getMentionedUsers().get(0).getIdLong());
                        event.getChannel().sendMessage("Permission revoked.").queue();
                    }
                    break;
                case "znd":
                    if (IsAllowed(Container.LauncherPermissionListZnD, author)) {
                        Container.LauncherPermissionListZnD.remove(event.getMessage().getMentionedUsers().get(0).getIdLong());
                        event.getChannel().sendMessage("Permission revoked.").queue();
                    }
                    break;
                case "rtm":
                    if (IsAllowed(Container.LauncherPermissionListRTM, author)) {
                        Container.LauncherPermissionListRTM.remove(event.getMessage().getMentionedUsers().get(0).getIdLong());
                        event.getChannel().sendMessage("Permission revoked.").queue();
                    }
                    break;
            }
        }
    }

    private boolean IsAllowed(List<Long> list, User author) {
        for (long i : list) {
            if (author.getIdLong() == i) {
                return true;
            }
        }
        //Check if MarkenJaden
        if (author.getIdLong() == 222733101770604545L) {
            return true;
        }
        return false;
    }
}
