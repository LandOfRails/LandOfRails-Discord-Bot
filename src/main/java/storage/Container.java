package storage;

import com.google.api.services.sheets.v4.Sheets;
import net.dv8tion.jda.api.entities.Guild;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Container {

    private Container() {

    }

    // Dateien
    public static final File VotingFile = new File("./VotingList.txt");
    public static final File SensitiveDataFile = new File("./Sensitive-data");
    public static final File CommandIdeas = new File("./CommandIdeas.txt");
    public static final File LauncherPermissionTC = new File("./LauncherPermissionTC.txt");
    public static final File LauncherPermissionIR = new File("./LauncherPermissionIR.txt");
    public static final File LauncherPermissionZnD = new File("./LauncherPermissionZnD.txt");
    public static final File LauncherPermissionRTM = new File("./LauncherPermissionRTM.txt");

    // Launcher
    public static final List<Long> LauncherPermissionListTC = new ArrayList<>();
    public static final List<Long> LauncherPermissionListIR = new ArrayList<>();
    public static final List<Long> LauncherPermissionListZnD = new ArrayList<>();
    public static final List<Long> LauncherPermissionListRTM = new ArrayList<>();

    // Abstimmungen
    public static final List<Long> ActiveVotings = new ArrayList<>();

    // Guild
    private static Guild guild;

    public static Guild getGuild() {
        return guild;
    }

    public static void setGuild(Guild g) {
        guild = g;
    }

    //Spreadsheet ID
    public static String spreadsheetId;
    public static Sheets sheetsService;
}
