import handler.LocalSafeHandler;
import handler.TimerTasks;
import listener.MessageListener;
import listener.ReactionListener;
import listener.ServerListener;
import listener.StartStopListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.Container;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main implements EventListener {
    public static void main(String[] args) throws LoginException {
        Logger logger = LogManager.getRootLogger();
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String sensitiveData = "";
        try {
            final Scanner s = new Scanner(Container.SensitiveDataFile);
            while (s.hasNext()) {
                sensitiveData = sensitiveData + s.next() + " ";
            }
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] sensitiveDataSplitted = sensitiveData.split(" ");

        String token = sensitiveDataSplitted[0];
        builder.setToken(token);
        builder.addEventListeners(new Main());
        builder.addEventListeners(new MessageListener());
        builder.addEventListeners(new ServerListener());
        builder.addEventListeners(new ReactionListener());
        builder.addEventListeners(new StartStopListener());
        builder.setActivity(Activity.watching("Ich werde gerade gewartet."));

        //Daten laden
        LocalSafeHandler.loadData();

        //Start
        builder.build();

        //Flyway (Databases)
        //Flyway flyway = Flyway.configure().dataSource("landofrails.net", "discord-bot", sensitiveDataSplitted[1]).load();
        //flyway.migrate();

        //TimerTasks starten
        TimerTasks tt = new TimerTasks();
        tt.checkActiveVotings();
    }

    @Override
    public void onEvent(@Nonnull GenericEvent event) {
        if (event instanceof Main) {
            System.out.println("API is ready!");
        }
    }
}
