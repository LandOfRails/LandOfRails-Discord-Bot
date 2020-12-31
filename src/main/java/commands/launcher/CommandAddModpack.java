package commands.launcher;

import com.google.gson.Gson;
import commands.interfaces.Command;
import model.Modpack;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang.math.NumberUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import static commands.utils.LauncherModpackUtils.getModpackList;

public class CommandAddModpack implements Command {

    @Override
    public String getName() {
        return "addModpack";
    }

    /**
     * !addModpack name title shortcut mcVersion modpackversion organisation key
     * locationonserver imageurl downloadedimage
     */

    @Override
    public void onCall(MessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split("\n");

        MessageChannel channel = event.getChannel();

        if (!hasPermission(event.getMember())) {
            channel.sendMessage("**Du hast keine Berechtigung dazu!**").queue();
            return;
        }

        if (args.length != 10) {
            // @formatter:off
            String response =
                    "Irgendwas ist da schiefgelaufen mmh, versuche es nochmal nach folgendem Aufbau:\n\n"
                            + "!addModpack **Universeller Name (ohne Leerzeichen, z.B. super_geiles_modpack)**\n"
                            + "**Titel der im Launcher angezeigt wird (z.B. SUPER GEILES MODPACK UNBEDINGT DOWNLOADEN)**\n"
                            + "**Abkürzung (z.B. ir)**\n"
                            + "**MC Version (z.B. 1.12.2)**\n"
                            + "**Modpack Version (z.B. 1.0.3**\n"
                            + "**An welcher Position befindet sich das Modpack (Zahl)**\n"
                            + "**Super geheimer Schlüssel (falls nicht benötigt einfach eine leere Zeile lassen)**\n"
                            + "**Download Link (genauso wie beim TechnicLauncher)**\n"
                            + "**Hintergrund Bild (Link)**\n"
                            + "**Icon vom Modpack wenn es heruntergeladen wurde (Link)**";
            // @formatter:on
            channel.sendMessage(response).queue();
        } else {
            String name = args[0].split(" ")[1];
            String title = args[1];
            String shortcut = args[2];
            String mcVersion = args[3];
            String modpackVersion = args[4];
            String organisationText = args[5];
            String key = args[6];
            String locationOnServer = args[7];
            String imageUrl = args[8];
            String downloadedImage = args[9];

            Integer organisation = NumberUtils.toInt(organisationText, -1);
            if (organisation < 1) {
                String response = "\"" + organisationText + "\" ist keine gültige Zahl!";
                channel.sendMessage(response).queue();
            } else {
                /** @formatter:off
                 *
                 *  TODO:
                 *  Eingaben verifizieren
                 *  Modpack verarbeiten
                 *
                 *  @formatter:on
                 */

                Modpack modpack = new Modpack(name, title, shortcut, modpackVersion, mcVersion, organisation, key,
                        locationOnServer, imageUrl, downloadedImage);

                List<Modpack> modpackList = getModpackList();

                boolean modpackValid = modpackList.stream().noneMatch(m -> m.equals(modpack));
                if (modpackValid) {
                    modpackList.add(modpack);

                    try (FileWriter fw = new FileWriter("/var/www/launcher/ModpackList.json"); BufferedWriter bw = new BufferedWriter(fw)) {
                        bw.write(new Gson().toJson(modpackList));
                        bw.flush();
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }

                    channel.sendMessage(modpack.toString()).queue();
                    channel.sendMessage("Erstellt!").queue();
                } else {
                    channel.sendMessage("Dieses Modpack existiert bereits.").queue();
                }

            }

        }

    }

    private boolean hasPermission(Member author) {
        if (author.isOwner())
            return true;

        for (Role r : author.getRoles()) {
            if (r.getName().equalsIgnoreCase("Management"))
                return true;
            if (r.getName().equalsIgnoreCase("Deputy Management"))
                return true;
        }

        return false;
    }

}
