package storage;

import net.dv8tion.jda.api.entities.Guild;

import java.io.File;
import java.util.ArrayList;

public class Container {

    //Dateien
    public static final File VotingFile = new File("./VotingList.txt");
    public static final File SensitiveDataFile = new File("./Sensitive-data");
    public static final File CommandIdeas = new File("./CommandIdeas.txt");

    //Abstimmungen
    public static ArrayList<Long> ActiveVotings = new ArrayList<>();

    //Guild
    public static Guild guild;

}
