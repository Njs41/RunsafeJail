package no.runsafe.runsafejail.commands;

import no.runsafe.framework.command.ExecutableCommand;
import no.runsafe.framework.server.ICommandExecutor;
import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.runsafejail.handlers.JailHandler;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.HashMap;

public class JailCommand extends ExecutableCommand
{
	public JailCommand(JailHandler jailHandler)
	{
		super("jail", "Jail a player in the specified jail", "runsafe.jail.<jail>", "player", "jail", "time");
		this.jailHandler = jailHandler;
		this.timeParser = new PeriodFormatterBuilder()
				.printZeroRarelyFirst().appendYears().appendSuffix("y", "years")
				.printZeroRarelyFirst().appendWeeks().appendSuffix("w", "weeks")
				.printZeroRarelyFirst().appendDays().appendSuffix("d", "days")
				.printZeroRarelyFirst().appendHours().appendSuffix("h", "hours")
				.printZeroRarelyFirst().appendMinutes().appendSuffix("m", "minutes")
				.printZeroRarelyFirst().appendSeconds().appendSuffix("s", "seconds")
				.toFormatter();
	}

	@Override
	public String OnExecute(ICommandExecutor executor, HashMap<String, String> parameters, String[] arguments)
	{
		RunsafePlayer target = RunsafeServer.Instance.getPlayer(parameters.get("player"));
		if (target != null)
		{
			if (target.isOnline())
			{
				String jail = parameters.get("jail");
				if (this.jailHandler.jailExists(jail))
				{
					Period duration = this.timeParser.parsePeriod(parameters.get("time"));
					this.jailHandler.jailPlayer(target, jail, DateTime.now().plus(duration));
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

	private final PeriodFormatter timeParser;
	private JailHandler jailHandler;
}
