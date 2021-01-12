package commands.launcher;

import com.google.gson.Gson;
import commands.interfaces.Command;
import model.Modpack;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import static commands.utils.LauncherModpackUtils.*;

public class CommandUpdateVersion implements Command {

    List<Modpack> modpackList;

    @Override
    public String getName() {
        return "updateVersion";
    }

    @Override
    public void onCall(MessageReceivedEvent event) {
        Member author = event.getMember();

        String[] args = event.getMessage().getContentRaw().split(" ", 3);
        if (args.length < 3) {
            event.getChannel().sendMessage("Please enter a valid server shortcut and version.").queue();
        } else {

            modpackList = getModpackList();

            for (Modpack m : modpackList) {
                if (m.getShortcut().equalsIgnoreCase(args[1])) {
                    if (IsAllowed(author, args[1])) {
                        model.Pair<Boolean, String> returned = updateVerison(m, args[2]);
                        if (Boolean.FALSE.equals(returned.getLeft())) {
                            event.getChannel().sendMessage("Please specify a higher version than " + returned.getRight()).queue();
                        } else
                            event.getChannel().sendMessage("Modpack has been updated to version " + returned.getRight() + ". Please also remember to change the link and version in the TechnicLauncher.").queue();
                    } else {
                        event.getChannel().sendMessage("You don't have permission to use this command for this modpack.").queue();
                    }
                }
            }
        }
    }


    private model.Pair<Boolean, String> updateVerison(Modpack modpack, String newVersion) {
        if (CompareVersions(modpack.getModpackVersion(), newVersion) == -1) {
            modpack.setModpackVersion(newVersion);
        } else {
            return new model.Pair<>(false, modpack.getModpackVersion());
        }

        try (FileWriter fw = new FileWriter("/var/www/launcher/ModpackList.json"); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(new Gson().toJson(modpackList));
            bw.flush();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return new model.Pair<>(true, newVersion);
    }
}
