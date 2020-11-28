package commands.launcher;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import commands.interfaces.Command;
import model.Modpack;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import storage.Container;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

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
                        if (!updateVerison("traincraft", args[2])) {
                            event.getChannel().sendMessage("Please specify a higher version than the previous one.").queue();
                        } else
                            event.getChannel().sendMessage("Modpack has been updated. Please also remember to change the link and version in the TechnicLauncher.").queue();
                    }
                    break;
                case "ir":
                    if (IsAllowed(Container.LauncherPermissionListIR, author)) {
                        if (!updateVerison("immersive_railroading_freebuild", args[2])) {
                            event.getChannel().sendMessage("Please specify a higher version than the previous one.").queue();
                        } else
                            event.getChannel().sendMessage("Modpack has been updated. Please also remember to change the link and version in the TechnicLauncher.").queue();
                    }
                    break;
                case "znd":
                    if (IsAllowed(Container.LauncherPermissionListZnD, author)) {
                        if (!updateVerison("zoranodensha", args[2])) {
                            event.getChannel().sendMessage("Please specify a higher version than the previous one.").queue();
                        } else
                            event.getChannel().sendMessage("Modpack has been updated. Please also remember to change the link and version in the TechnicLauncher.").queue();
                    }
                    break;
                case "rtm":
                    if (IsAllowed(Container.LauncherPermissionListRTM, author)) {
                        if (!updateVerison("realtrainmod", args[2])) {
                            event.getChannel().sendMessage("Please specify a higher version than the previous one.").queue();
                        } else
                            event.getChannel().sendMessage("Modpack has been updated. Please also remember to change the link and version in the TechnicLauncher.").queue();
                    }
                    break;
            }
        }
    }

    private boolean updateVerison(String modpackName, String newVersion) {
        List<Modpack> modpackList;
        String json = "";
        File jsonFile = new File("/var/www/launcher/ModpackList.json");
        try {
            Scanner s = new Scanner(jsonFile);
            while (s.hasNextLine()) {
                json += s.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        modpackList = new Gson().fromJson(json, new TypeToken<List<Modpack>>() {
        }.getType());
        for (Modpack m : modpackList) {
            if (m.getName().equals(modpackName)) {
                if (CompareVersions(m.getModpackVersion(), newVersion) == -1) {
                    m.setModpackVersion(newVersion);
                } else return false;
                break;
            }
        }

        try (FileWriter fw = new FileWriter(jsonFile); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(new Gson().toJson(modpackList));
            bw.flush();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private int CompareVersions(String version1, String version2) {
        String[] string1Vals = version1.split("\\.");
        String[] string2Vals = version2.split("\\.");

        int length = Math.max(string1Vals.length, string2Vals.length);

        for (int i = 0; i < length; i++) {
            Integer v1 = (i < string1Vals.length) ? Integer.parseInt(string1Vals[i]) : 0;
            Integer v2 = (i < string2Vals.length) ? Integer.parseInt(string2Vals[i]) : 0;

            //Making sure Version1 bigger than version2
            if (v1 > v2) {
                return 1;
            }
            //Making sure Version1 smaller than version2
            else if (v1 < v2) {
                return -1;
            }
        }

        //Both are equal
        return 0;
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
