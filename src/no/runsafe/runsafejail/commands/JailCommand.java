package no.runsafe.runsafejail.commands;

import no.runsafe.framework.command.ExecutableCommand;
import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.server.ICommandExecutor;
import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.runsafejail.Jail;
import no.runsafe.runsafejail.handlers.JailHandler;

import java.util.HashMap;

public class JailCommand extends ExecutableCommand
{
	public JailCommand(JailHandler jailHandler)
	{
		super("jail", "Jail a player in the specified jail", "runsafe.jail.<jail>", "player", "jail", "time");
		this.jailHandler = jailHandler;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, HashMap<String, String> parameters, String[] arguments)
	{
		RunsafePlayer target = RunsafeServer.Instance.getPlayer(parameters.get("player"));
		if (target != null)
		{
			if (target.isOnline())
			{
				Jail theJail = this.jailHandler.getJailByName(parameters.get("jail"));
				if (theJail != null)
				{
					theJail.teleportPlayerHere(target);
				}
				else
				{
					return "&c" + parameters.get("jail") + " is not a valid jail.";
				}
			}
			else
			{
				return "&c" + target.getName() + " is not online.";
			}
		}
		return "&cThe specified player does not exist.";
	}

	@Override
	public String OnExecute(ICommandExecutor executor, HashMap<String, String> parameters)
	{
		return null;
	}

	private JailHandler jailHandler;
}
