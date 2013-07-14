package no.runsafe.runsafejail.commands;

import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.runsafejail.exceptions.JailPlayerException;
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

		try
		{
			Period duration = this.timeParser.parsePeriod(parameters.get("time"));
			this.jailHandler.jailPlayer(target, parameters.get("jail"), DateTime.now().plus(duration));
			return String.format("%s has been jailed for %s", parameters.get("player"), parameters.get("time"));
		}
		catch (JailPlayerException e)
		{
			return String.format("&c%s", e.getMessage());
		}
	}

	@Override
	public String OnExecute(ICommandExecutor executor, HashMap<String, String> parameters)
	{
		return null;
	}

	private PeriodFormatter timeParser;
	private JailHandler jailHandler;
}
