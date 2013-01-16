package no.runsafe.runsafejail.database;

import no.runsafe.framework.database.IDatabase;
import no.runsafe.framework.database.Repository;
import no.runsafe.framework.output.IOutput;
import no.runsafe.runsafejail.JailSentence;
import org.joda.time.DateTime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JailedPlayersDatabase extends Repository
{
	public JailedPlayersDatabase(IDatabase database, IOutput console)
	{
		this.console = console;
		this.database = database;
	}

	@Override
	public String getTableName()
	{
		return "jailed_players";
	}

	@Override
	public HashMap<Integer, List<String>> getSchemaUpdateQueries()
	{
		HashMap<Integer, List<String>> queries = new HashMap<Integer, List<String>>();
		ArrayList<String> sql = new ArrayList<String>();
		sql.add(
			"CREATE TABLE `jailed_players` (" +
					"`player` VARCHAR(20) NOT NULL," +
					"`jail` VARCHAR(30) NOT NULL," +
					"`start` DATETIME NOT NULL," +
					"`end` DATETIME NOT NULL," +
					"PRIMARY KEY (`player`)" +
					")"
		);
		queries.put(1, sql);
		return queries;
	}

	public HashMap<String, JailSentence> getJailedPlayers()
	{
		PreparedStatement select = database.prepare("SELECT player, jail, start, end FROM jailed_players");
		HashMap<String, JailSentence> jailedPlayers = new HashMap<String, JailSentence>();

		try
		{
			ResultSet results = select.executeQuery();
			while (results.next())
			{
				jailedPlayers.put(results.getString("player"), new JailSentence(
						results.getString("player"),
						results.getString("jail"),
						convert(results.getTimestamp("end"))
				));
			}
		}
		catch (SQLException e)
		{
			this.console.write(e.getMessage());
		}

		return jailedPlayers;
	}

	public void addJailedPlayer(String playerName, String jailName, DateTime end)
	{
		PreparedStatement update = database.prepare(
				"INSERT IGNORE INTO jailed_players (player, jail, start, end)" +
						"VALUES(?, ?, NOW(), ?)"
		);

		try
		{
			update.setString(1, playerName);
			update.setString(2, jailName);
			update.setTimestamp(3, convert(end));
			update.executeUpdate();
		}
		catch (SQLException e)
		{
			this.console.write(e.getMessage());
		}
	}

	public void removeJailedPlayer(String playerName)
	{
		PreparedStatement delete = database.prepare("DELETE FROM jailed_players WHERE player = ?");

		try
		{
			delete.setString(1, playerName);
			delete.executeQuery();
		}
		catch (SQLException e)
		{
			this.console.write(e.getMessage());
		}
	}

	private IDatabase database;
	private IOutput console;
}
