package commands.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Modpack;
import net.dv8tion.jda.api.entities.Member;
import storage.Container;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class LauncherModpackUtils {

    private LauncherModpackUtils() {

    }

    public static int CompareVersions(String version1, String version2) {
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

    public static List<Modpack> getModpackList() {
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
        return new Gson().fromJson(json, new TypeToken<List<Modpack>>() {
        }.getType());
    }

    public static boolean IsAllowed(Member author, String shortcut) {
        if (author.isOwner()) return true;
        try {
            Connection conn = Container.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MemberID FROM launcherAccess WHERE MemberID=" + author.getId() + " AND ModpackShortcut='" + shortcut.toLowerCase(Locale.ROOT) + "'");
            rs.last();
            stmt.close();
            conn.close();
            return rs.getRow() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

}
