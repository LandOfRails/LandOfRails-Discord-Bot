package listener;

import javax.annotation.Nonnull;

import handler.LocalSafeHandler;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import storage.Container;

public class StartStopListener extends ListenerAdapter {

	@Override
	public void onShutdown(@Nonnull ShutdownEvent event) {
		LocalSafeHandler.saveData();
	}

	@Override
	public void onReady(@Nonnull ReadyEvent event) {
		Container.setGuild(event.getJDA().getGuildById(394112479283904512L));
	}
}
