package commands.launcher;

import commands.interfaces.Command;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import storage.Container;

import static commands.utils.LauncherModpackUtils.IsAllowed;

public class CommandAddPermission implements Command {
    @Override
    public String getName() {
        return "addPermission";
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
                        Container.LauncherPermissionListTC.add(event.getMessage().getMentionedUsers().get(0).getIdLong());
                        event.getChannel().sendMessage("Permission granted.").queue();
                    } else {
                        event.getChannel().sendMessage("You don't have permission to use this command.").queue();
                    }
                    break;
                case "ir":
                    if (IsAllowed(Container.LauncherPermissionListIR, author)) {
                        Container.LauncherPermissionListIR.add(event.getMessage().getMentionedUsers().get(0).getIdLong());
                        event.getChannel().sendMessage("Permission granted.").queue();
                    } else {
                        event.getChannel().sendMessage("You don't have permission to use this command.").queue();
                    }
                    break;
                case "znd":
                    if (IsAllowed(Container.LauncherPermissionListZnD, author)) {
                        Container.LauncherPermissionListZnD.add(event.getMessage().getMentionedUsers().get(0).getIdLong());
                        event.getChannel().sendMessage("Permission granted.").queue();
                    } else {
                        event.getChannel().sendMessage("You don't have permission to use this command.").queue();
                    }
                    break;
                case "rtm":
                    if (IsAllowed(Container.LauncherPermissionListRTM, author)) {
                        Container.LauncherPermissionListRTM.add(event.getMessage().getMentionedUsers().get(0).getIdLong());
                        event.getChannel().sendMessage("Permission granted.").queue();
                    } else {
                        event.getChannel().sendMessage("You don't have permission to use this command.").queue();
                    }
                    break;
            }
        }
    }
}
