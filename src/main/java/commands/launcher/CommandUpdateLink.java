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

public class CommandUpdateLink implements Command {

    @Override
    public String getName() {
        return "updateLink";
    }

    @Override
    public void onCall(MessageReceivedEvent event) {
        Member author = event.getMember();

        String[] args = event.getMessage().getContentRaw().split(" ", 3);
        if (args.length < 3) {
            event.getChannel().sendMessage("Please enter a valid server shortcut and link.").queue();
        } else {
            List<Modpack> modpackList = getModpackList();
            boolean modpackUpdated = false;
            for (Modpack m : modpackList) {
                if (args[1].equalsIgnoreCase(m.getShortcut())) {
                    if (IsAllowed(author, args[1])) {
                        m.setLocationOnServer(args[2]);
                        modpackUpdated = true;
                        String result = "";
                        try {
                            String version = m.getModpackVersion();
                            System.out.println(version);
                            String[] versionNumbers = version.split("\\.");
                            int lastNumber = Integer.parseInt(versionNumbers[versionNumbers.length - 1]);
                            result = "";
                            for (int i = 0; i < versionNumbers.length - 1; i++) {
                                result += versionNumbers[i] + ".";
                            }
                            result += (lastNumber + 1);
                        } catch (Exception e) {
                            result = "Error getModpackVersion";
                        } finally {
                            event.getChannel().sendMessage("Download link changed. Remember to also change the version to push an update. **Suggestion:** !updateVersion " + args[1] + " " + result).queue();
                        }
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
//            switch (args[1].toLowerCase()) {
//                case "tc":
//                    if (IsAllowed(Container.LauncherPermissionListTC, author)) {
//                        updateLink("traincraft", args[2]);
//                        event.getChannel().sendMessage("Download link changed. Remember to also change the version to push an update. **Suggestion:** !updateVersion tc " + getGreaterModpackVersion("traincraft")).queue();
//                    } else {
//                        event.getChannel().sendMessage("You don't have permission to use this command.").queue();
//                    }
//                    break;
//                case "ir":
//                    if (IsAllowed(Container.LauncherPermissionListIR, author)) {
//                        updateLink("immersive_railroading_freebuild", args[2]);
//                        event.getChannel().sendMessage("Download link changed. Remember to also change the version to push an update. **Suggestion:** !updateVersion ir " + getGreaterModpackVersion("immersive_railroading_freebuild")).queue();
//                    } else {
//                        event.getChannel().sendMessage("You don't have permission to use this command.").queue();
//                    }
//                    break;
//                case "znd":
//                    if (IsAllowed(Container.LauncherPermissionListZnD, author)) {
//                        updateLink("zoranodensha", args[2]);
//                        event.getChannel().sendMessage("Download link changed. Remember to also change the version to push an update. **Suggestion:** !updateVersion znd " + getGreaterModpackVersion("zoranodensha")).queue();
//                    } else {
//                        event.getChannel().sendMessage("You don't have permission to use this command.").queue();
//                    }
//                    break;
//                case "rtm":
//                    if (IsAllowed(Container.LauncherPermissionListRTM, author)) {
//                        updateLink("realtrainmod", args[2]);
//                        event.getChannel().sendMessage("Download link changed. Remember to also change the version to push an update. **Suggestion:** !updateVersion rtm " + getGreaterModpackVersion("realtrainmod")).queue();
//                    } else {
//                        event.getChannel().sendMessage("You don't have permission to use this command.").queue();
//                    }
//                    break;
//            }
        }
    }
}
