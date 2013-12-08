package no.runsafe.runsafejail.commands;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.PlayerArgument;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.runsafejail.exceptions.JailPlayerException;
import no.runsafe.runsafejail.handlers.JailHandler;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.Map;

public class JailCommand extends ExecutableCommand
{
	public JailCommand(JailHandler jailHandler, IServer server)
	{
		super("jail", "Jail a player in the specified jail", "runsafe.jail.<jail>", new PlayerArgument(), new RequiredArgument("jail"), new RequiredArgument("time"));
		this.jailHandler = jailHandler;
		this.server = server;
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
	public String OnExecute(ICommandExecutor executor, Map<String, String> parameters)
	{
		IPlayer target = server.getPlayer(parameters.get("player"));

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

	private PeriodFormatter timeParser;
	private JailHandler jailHandler;
	private final IServer server;
}
