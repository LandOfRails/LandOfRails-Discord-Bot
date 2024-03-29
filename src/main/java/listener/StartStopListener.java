package listener;

import handler.LocalSafeHandler;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import storage.Container;

import javax.annotation.Nonnull;

public class StartStopListener extends ListenerAdapter {

    @Override
    public void onShutdown(@Nonnull ShutdownEvent event) {
        LocalSafeHandler.saveData(Container.VotingFile, Container.ActiveVotings);
        System.exit(event.getCode());
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        System.out.println("Readyyy");
        Container.setGuild(event.getJDA().getGuildById(394112479283904512L));
        ReactionListener.checkIfReacted();
        ReactionListener.checkIfUsersGotRole();
    }
}
