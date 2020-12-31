package commands.launcher;

import com.google.gson.Gson;
import commands.interfaces.Command;
import model.Modpack;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import static commands.utils.LauncherModpackUtils.IsAllowed;
import static commands.utils.LauncherModpackUtils.getModpackList;

public class CommandUpdateImageLink implements Command {
    @Override
    public String getName() {
        return "updateImage";
    }

    @Override
    public void onCall(MessageReceivedEvent event) {
        Member author = event.getMember();

        String[] args = event.getMessage().getContentRaw().split(" ", 3);
        if (args.length < 3) {
            event.getChannel().sendMessage("Please enter a valid server shortcut and link.").queue();
        } else {
            boolean modpackUpdated = false;
            List<Modpack> modpackList = getModpackList();
            for (Modpack m : modpackList) {
                if (m.getShortcut().equalsIgnoreCase(args[1])) {
                    if (IsAllowed(author, args[1])) {
                        m.setImageUrl(args[2]);
                        event.getChannel().sendMessage("Image updated.").queue();
                        modpackUpdated = true;
                    } else
                        event.getChannel().sendMessage("You don't have permission to use this command for this modpack.").queue();
                }
            }
            if (modpackUpdated) {
                try (FileWriter fw = new FileWriter("/var/www/launcher/ModpackList.json"); BufferedWriter bw = new BufferedWriter(fw)) {
                    bw.write(new Gson().toJson(modpackList));
                    bw.flush();
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
