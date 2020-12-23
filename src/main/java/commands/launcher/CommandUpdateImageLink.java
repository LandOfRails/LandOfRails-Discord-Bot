package commands.launcher;

import com.google.gson.Gson;
import commands.interfaces.Command;
import model.Modpack;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import storage.Container;

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
        User author = event.getAuthor();

        String[] args = event.getMessage().getContentRaw().split(" ", 3);
        if (args.length < 3) {
            event.getChannel().sendMessage("Please enter a valid server shortcut and link.").queue();
        } else {
            switch (args[1].toLowerCase()) {
                case "tc":
                    if (IsAllowed(Container.LauncherPermissionListTC, author)) {
                        updateImageLink("traincraft", args[2]);
                        event.getChannel().sendMessage("Image link changed.").queue();
                    }
                    break;
                case "ir":
                    if (IsAllowed(Container.LauncherPermissionListIR, author)) {
                        updateImageLink("immersive_railroading_freebuild", args[2]);
                        event.getChannel().sendMessage("Image link changed.").queue();
                    }
                    break;
                case "znd":
                    if (IsAllowed(Container.LauncherPermissionListZnD, author)) {
                        updateImageLink("zoranodensha", args[2]);
                        event.getChannel().sendMessage("Image link changed.").queue();
                    }
                    break;
                case "rtm":
                    if (IsAllowed(Container.LauncherPermissionListRTM, author)) {
                        updateImageLink("realtrainmod", args[2]);
                        event.getChannel().sendMessage("Image link changed.").queue();
                    }
                    break;
            }
        }
    }

    public static void updateImageLink(String modpackName, String newLink) {
        List<Modpack> modpackList = getModpackList();
        for (Modpack m : modpackList) {
            if (m.getName().equals(modpackName)) {
                m.setImageUrl(newLink);
                break;
            }
        }

        try (FileWriter fw = new FileWriter("/var/www/launcher/ModpackList.json"); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(new Gson().toJson(modpackList));
            bw.flush();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}
