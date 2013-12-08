package no.runsafe.runsafejail.handlers;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.ITimer;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.RunsafeWorld;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.runsafejail.database.JailedPlayerDatabaseObject;
import no.runsafe.runsafejail.database.JailedPlayersDatabase;
import no.runsafe.runsafejail.database.JailsDatabase;
import no.runsafe.runsafejail.exceptions.JailPlayerException;
import no.runsafe.runsafejail.objects.JailSentence;
import no.runsafe.runsafejail.objects.JailedPlayer;
import no.runsafe.runsafejail.workers.TetherWorker;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class JailHandler implements IConfigurationChanged
{
	public JailHandler(JailsDatabase jailsDatabase, JailedPlayersDatabase jailedPlayersDatabase, IOutput console, JailSentenceFactory jailSentenceFactory, TetherWorker tetherWorker, IServer server)
	{
		this.jailsDatabase = jailsDatabase;
		this.console = console;
		this.jailedPlayersDatabase = jailedPlayersDatabase;
		this.jailSentenceFactory = jailSentenceFactory;
		this.server = server;
		this.jailSentenceFactory.setJailHandler(this);
		this.tetherWorker = tetherWorker;
		this.tetherWorker.setJailHandler(this);
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.loadJailsFromDatabase();
		this.loadJailedPlayersFromDatabase();

		this.jailTether = configuration.getConfigValueAsInt("tether");
	}

	private void loadJailsFromDatabase()
	{
		this.jails = this.jailsDatabase.getJails();
		this.console.outputDebugToConsole("Loaded %s jails from the database.", Level.INFO, this.jails.size());
	}

	private void loadJailedPlayersFromDatabase()
	{
		this.cancelAllJailTimers();
		this.jailedPlayers = new HashMap<String, JailSentence>();
		List<JailedPlayerDatabaseObject> jailedPlayers = this.jailedPlayersDatabase.getJailedPlayers();

		for (JailedPlayerDatabaseObject playerData : jailedPlayers)
		{
			IPlayer player = server.getPlayer(playerData.getPlayerName());

			if (player != null)
			{
				JailedPlayer jailedPlayer = (JailedPlayer) player;
				jailedPlayer.setReturnLocation(new RunsafeLocation(
					new RunsafeWorld(playerData.getReturnWorld()),
					playerData.getReturnX(),
					playerData.getReturnY(),
					playerData.getReturnZ()
				));

				try
				{
					this.jailPlayer(jailedPlayer, playerData.getJailName(), playerData.getSentenceEnd());
				}
				catch (JailPlayerException e)
				{
					this.console.outputDebugToConsole(
						"Failed to load jail sentence for %s in jail %s: %s",
						Level.WARNING,
						jailedPlayer.getName(),
						playerData.getJailName(),
						e.getMessage()
					);
				}
			}
		}
		this.console.outputDebugToConsole("Loaded %s jail sentences from the database.", Level.INFO, this.jailedPlayers.size());
	}

	private boolean jailExists(String jailName)
	{
		return this.jails.containsKey(jailName);
	}

	private boolean playerIsJailed(String playerName)
	{
		return this.jailedPlayers.containsKey(playerName);
	}

	public String getPlayerJail(String playerName)
	{
		if (this.playerIsJailed(playerName))
			return this.jailedPlayers.get(playerName).getJailName();

		return null;
	}

	public RunsafeLocation getPlayerJailLocation(String playerName)
	{
		String jailName = this.getPlayerJail(playerName);

		if (jailName != null)
			return this.getJailLocation(jailName);

		return null;
	}

	private void cancelAllJailTimers()
	{
		if (this.jailedPlayers != null)
			for (JailSentence jailSentence : this.jailedPlayers.values())
				jailSentence.getJailTimer().stop();
	}

	public void jailPlayer(RunsafePlayer player, String jailName, DateTime end) throws JailPlayerException
	{
		if (player != null)
		{
			if (this.jailExists(jailName))
			{
				String playerName = player.getName();
				if (!this.playerIsJailed(playerName))
				{
					Duration duration = new Duration(DateTime.now(), end);
					long remainingTime = duration.getStandardSeconds() * 20;

					JailedPlayer jailedPlayer = new JailedPlayer(player.getRawPlayer());
					if (!jailedPlayer.hasReturnLocation()) jailedPlayer.setReturnLocation();

					JailSentence jailSentence = new JailSentence(jailName, end, this.jailSentenceFactory.create(
						jailedPlayer,
						remainingTime,
						false
					));

					this.jailedPlayers.put(playerName, jailSentence);
					this.console.outputDebugToConsole(
						"Jailing player %s for %s ticks", Level.INFO, playerName, remainingTime
					);
					this.jailedPlayersDatabase.addJailedPlayer(jailedPlayer, jailSentence);
					jailedPlayer.teleport(this.getJailLocation(jailName));
					this.tetherWorker.Push(jailedPlayer.getName(), null);
				}
				else
				{
					throw new JailPlayerException("That player is already in jail.");
				}
			}
			else
			{
				throw new JailPlayerException("The specified jail does not exist.");
			}
		}
		else
		{
			throw new JailPlayerException("The specified player does not exist.");
		}
	}

	public void unjailPlayer(RunsafePlayer player) throws JailPlayerException
	{
		String playerName = player.getName();
		if (this.playerIsJailed(playerName))
		{
			ITimer jailTimer = this.jailedPlayers.get(playerName).getJailTimer();
			jailTimer.resetTicks(0L);
		}
		else
		{
			throw new JailPlayerException("The specified player is not in jail.");
		}
	}

	public void removeJailSentence(String playerName)
	{
		if (this.playerIsJailed(playerName))
		{
			this.jailedPlayers.remove(playerName);
			this.jailedPlayersDatabase.removeJailedPlayer(playerName);
		}
	}

	public RunsafeLocation getJailLocation(String jailName)
	{
		if (this.jailExists(jailName))
			return this.jails.get(jailName);

		return null;
	}

	public int getJailTether()
	{
		return this.jailTether;
	}

	private HashMap<String, RunsafeLocation> jails;
	private HashMap<String, JailSentence> jailedPlayers;

	private JailsDatabase jailsDatabase;
	private JailedPlayersDatabase jailedPlayersDatabase;
	private JailSentenceFactory jailSentenceFactory;

	private IOutput console;

	private int jailTether = 20;
	private TetherWorker tetherWorker;
	private final IServer server;
}
