package handler;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class LocalSafeHandler {

    private LocalSafeHandler() {

    }

    public static void loadData(File file, List list) {

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        } else {
            try (Scanner s = new Scanner(file)) {
                while (s.hasNextLong()) {
                    list.add(s.nextLong());
                }
            } catch (final FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveData(File file, List list) {

        try (FileWriter fw = new FileWriter(file); BufferedWriter bw = new BufferedWriter(fw)) {
            for (Object i : list) {
                bw.write("" + i);
                bw.newLine();
            }
            bw.flush();
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

}
