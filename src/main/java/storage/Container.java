package storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.Guild;

public class Container {

	private Container() {

	}

	// Dateien
	public static final File VotingFile = new File("./VotingList.txt");
	public static final File SensitiveDataFile = new File("./Sensitive-data");
	public static final File CommandIdeas = new File("./CommandIdeas.txt");

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

}
