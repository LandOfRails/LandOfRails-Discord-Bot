package commands.launcher;

import commands.interfaces.Command;
import model.Modpack;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import storage.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static commands.utils.LauncherModpackUtils.IsAllowed;
import static commands.utils.LauncherModpackUtils.getModpackList;

public class CommandRemovePermission implements Command {
    @Override
    public String getName() {
        return "removePermission";
    }

    @Override
    public void onCall(MessageReceivedEvent event) {
        Member author = event.getMember();

        String[] args = event.getMessage().getContentRaw().split(" ", 3);
        if (args.length < 3) {
            event.getChannel().sendMessage("Please enter a valid server shortcut.").queue();
        } else {
            List<Modpack> modpackList = getModpackList();
            for (Modpack m : modpackList) {
                if (m.getShortcut().equalsIgnoreCase(args[1])) {
                    if (IsAllowed(author, args[1])) {
                        try {
                            Connection conn = Container.getConnection();
                            Statement stmt = conn.createStatement();
                            stmt.execute("DELETE FROM launcherAccess WHERE MemberID=" + event.getMessage().getMentionedMembers().get(0).getId());
                            event.getChannel().sendMessage("Permission revoked.").queue();
                            stmt.close();
                            conn.close();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    } else
                        event.getChannel().sendMessage("You don't have permission to use this command for this modpack.").queue();
                }
            }
        }
    }
}
