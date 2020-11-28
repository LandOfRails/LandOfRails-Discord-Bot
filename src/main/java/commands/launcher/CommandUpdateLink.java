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

public class CommandUpdateLink implements Command {
    @Override
    public String getName() {
        return "updateLink";
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
                        updateLink("traincraft", args[2]);
                        event.getChannel().sendMessage("Download link changed. Remember to also change the version to push an update.").queue();
                    }
                    break;
                case "ir":
                    if (IsAllowed(Container.LauncherPermissionListIR, author)) {
                        updateLink("immersive_railroading_freebuild", args[2]);
                        event.getChannel().sendMessage("Download link changed. Remember to also change the version to push an update.").queue();
                    }
                    break;
                case "znd":
                    if (IsAllowed(Container.LauncherPermissionListZnD, author)) {
                        updateLink("zoranodensha", args[2]);
                        event.getChannel().sendMessage("Download link changed. Remember to also change the version to push an update.").queue();
                    }
                    break;
                case "rtm":
                    if (IsAllowed(Container.LauncherPermissionListRTM, author)) {
                        updateLink("realtrainmod", args[2]);
                        event.getChannel().sendMessage("Download link changed. Remember to also change the version to push an update.").queue();
                    }
                    break;
            }
        }
    }

    private void updateLink(String modpackName, String newLink) {
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
                m.setLocationOnServer(newLink);
                break;
            }
        }

        try (FileWriter fw = new FileWriter(jsonFile); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(new Gson().toJson(modpackList));
            bw.flush();
        } catch (final Exception e) {
            e.printStackTrace();
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
