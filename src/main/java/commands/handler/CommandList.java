package commands.handler;

import java.util.ArrayList;
import java.util.List;

import commands.CommandAFK;
import commands.CommandAbstimmung;
import commands.CommandFunCommands;
import commands.CommandHelp;
import commands.CommandIdea;
import commands.CommandInfo;
import commands.CommandRemove;
import commands.CommandStop;
import commands.interfaces.Command;

public class CommandList {

	private List<Command> commands = new ArrayList<>();

	public CommandList() {
		commands.add(new CommandAbstimmung());
		commands.add(new CommandAFK());
		commands.add(new CommandFunCommands());
		commands.add(new CommandHelp());
		commands.add(new CommandIdea());
		commands.add(new CommandInfo());
		commands.add(new CommandRemove());
		commands.add(new CommandStop());
	}

	public List<Command> getCommands() {
		return commands;
	}

}
