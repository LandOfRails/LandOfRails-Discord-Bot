package commands.handler;

import commands.*;
import commands.interfaces.Command;
import commands.launcher.*;

import java.util.ArrayList;
import java.util.List;

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
        commands.add(new CommandAbstimmungNeu());
    }

    public List<Command> getCommands() {
        return commands;
    }

}
