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

import static commands.utils.LauncherModpackUtils.*;

public class CommandUpdateVersion implements Command {
    @Override
    public String getName() {
        return "updateVersion";
    }

    @Override
    public void onCall(MessageReceivedEvent event) {
        User author = event.getAuthor();

        String[] args = event.getMessage().getContentRaw().split(" ", 3);
        if (args.length < 3) {
            event.getChannel().sendMessage("Please enter a valid server shortcut and version.").queue();
        } else {
            switch (args[1].toLowerCase()) {
                case "tc":
                    if (IsAllowed(Container.LauncherPermissionListTC, author)) {
                        model.Pair<Boolean, String> returned = updateVerison("traincraft", args[2]);
                        if (!returned.getLeft()) {
                            event.getChannel().sendMessage("Please specify a higher version than " + returned.getRight().toString()).queue();
                        } else
                            event.getChannel().sendMessage("Modpack has been updated to version " + returned.getRight() + ". Please also remember to change the link and version in the TechnicLauncher.").queue();
                    } else {
                        event.getChannel().sendMessage("You don't have permission to use this command.").queue();
                    }
                    break;
                case "ir":
                    if (IsAllowed(Container.LauncherPermissionListIR, author)) {
                        model.Pair<Boolean, String> returned = updateVerison("immersive_railroading_freebuild", args[2]);
                        if (!returned.getLeft()) {
                            event.getChannel().sendMessage("Please specify a higher version than " + returned.getRight()).queue();
                        } else
                            event.getChannel().sendMessage("Modpack has been updated to version " + returned.getRight() + ". Please also remember to change the link and version in the TechnicLauncher.").queue();
                    } else {
                        event.getChannel().sendMessage("You don't have permission to use this command.").queue();
                    }
                    break;
                case "znd":
                    if (IsAllowed(Container.LauncherPermissionListZnD, author)) {
                        model.Pair<Boolean, String> returned = updateVerison("zoranodensha", args[2]);
                        if (!returned.getLeft()) {
                            event.getChannel().sendMessage("Please specify a higher version than " + returned.getRight()).queue();
                        } else
                            event.getChannel().sendMessage("Modpack has been updated to version " + returned.getRight() + ". Please also remember to change the link and version in the TechnicLauncher.").queue();
                    } else {
                        event.getChannel().sendMessage("You don't have permission to use this command.").queue();
                    }
                    break;
                case "rtm":
                    if (IsAllowed(Container.LauncherPermissionListRTM, author)) {
                        model.Pair<Boolean, String> returned = updateVerison("realtrainmod", args[2]);
                        if (!returned.getLeft()) {
                            event.getChannel().sendMessage("Please specify a higher version than " + returned.getRight()).queue();
                        } else
                            event.getChannel().sendMessage("Modpack has been updated to version " + returned.getRight() + ". Please also remember to change the link and version in the TechnicLauncher.").queue();
                    } else {
                        event.getChannel().sendMessage("You don't have permission to use this command.").queue();
                    }
                    break;
            }
        }
    }


    private model.Pair<Boolean, String> updateVerison(String modpackName, String newVersion) {
        List<Modpack> modpackList = getModpackList();
        for (Modpack m : modpackList) {
            if (m.getName().equals(modpackName)) {
                if (CompareVersions(m.getModpackVersion(), newVersion) == -1) {
                    m.setModpackVersion(newVersion);
                } else {
                    return new model.Pair<>(false, m.getModpackVersion());
                }
                break;
            }
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
