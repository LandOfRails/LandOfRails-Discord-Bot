package commands.handler;

import java.util.ArrayList;
import java.util.List;

import commands.CommandAFK;
import commands.CommandAbstimmung;
import commands.CommandCertificates;
import commands.CommandFunCommands;
import commands.CommandHelp;
import commands.CommandIdea;
import commands.CommandInfo;
import commands.CommandRemove;
import commands.CommandStop;
import commands.CommandTest;
import commands.interfaces.Command;
import commands.launcher.CommandAddModpack;
import commands.launcher.CommandAddPermission;
import commands.launcher.CommandRemoveModpack;
import commands.launcher.CommandRemovePermission;
import commands.launcher.CommandUpdateImageLink;
import commands.launcher.CommandUpdateLink;
import commands.launcher.CommandUpdateVersion;

public class CommandList {

	private List<Command> commands = new ArrayList<>();

	public static final CommandAFK AFKCOMMAND = new CommandAFK();

	public CommandList() {
		commands.add(new CommandAbstimmung());
		commands.add(AFKCOMMAND);
		commands.add(new CommandFunCommands());
		commands.add(new CommandHelp());
		commands.add(new CommandIdea());
		commands.add(new CommandInfo());
		commands.add(new CommandRemove());
		commands.add(new CommandStop());
		commands.add(new CommandTest());
		commands.add(new CommandUpdateLink());
		commands.add(new CommandAddPermission());
		commands.add(new CommandRemovePermission());
		commands.add(new CommandUpdateVersion());
		commands.add(new CommandUpdateImageLink());
		commands.add(new CommandAddModpack());
		commands.add(new CommandRemoveModpack());
		commands.add(new CommandCertificates());
	}

	public List<Command> getCommands() {
		return commands;
	}

}
