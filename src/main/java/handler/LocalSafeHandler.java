package handler;

import storage.Container;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LocalSafeHandler {

    public static void loadData() {

        if (!Container.VotingFile.exists()) {
            try {
                Container.VotingFile.createNewFile();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                final Scanner s = new Scanner(Container.VotingFile);
                while (s.hasNextLong()) {
                    Container.ActiveVotings.add(s.nextLong());
                }
            } catch (final FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public static void saveData() {

        try {
            final FileWriter fw = new FileWriter(Container.VotingFile);
            final BufferedWriter bw = new BufferedWriter(fw);
            for (Long i : Container.ActiveVotings) {
                bw.write("" + i);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

}
