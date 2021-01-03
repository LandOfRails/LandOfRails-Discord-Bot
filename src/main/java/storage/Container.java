package storage;

import com.google.api.services.sheets.v4.Sheets;
import model.Modpack;
import model.Triple;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Container {

    // Dateien
    public static final File VotingFile = new File("./VotingList.txt");
    public static final File SensitiveDataFile = new File("./Sensitive-data");
    public static final File CommandIdeas = new File("./CommandIdeas.txt");

    // Abstimmungen
    public static final List<Long> ActiveVotings = new ArrayList<>();

    // Guild
    private static Guild guild;

    public Container(String connection) {
        this.connection = connection;
    }

    public static Guild getGuild() {
        return guild;
    }

    public static void setGuild(Guild g) {
        guild = g;
    }

    //Spreadsheet ID
    public static String spreadsheetId;
    public static Sheets sheetsService;

    //MessageID, MemberID, Modpack
    public static List<Triple<Message, Long, Modpack>> modpackDeletionList = new ArrayList<>();

    //DB Connection
    private static String connection;

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
