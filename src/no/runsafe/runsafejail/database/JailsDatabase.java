package no.runsafe.runsafejail.database;

import no.runsafe.framework.database.IDatabase;
import no.runsafe.framework.database.Repository;
import no.runsafe.framework.output.IOutput;
import no.runsafe.framework.server.RunsafeLocation;
import no.runsafe.framework.server.RunsafeWorld;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JailsDatabase extends Repository
{
	public JailsDatabase(IDatabase database, IOutput console)
	{
		this.database = database;
		this.console = console;
	}

	@Override
	public String getTableName()
	{
		return "jails";
	}

	@Override
	public HashMap<Integer, List<String>> getSchemaUpdateQueries()
	{
		HashMap<Integer, List<String>> queries = new HashMap<Integer, List<String>>();
		ArrayList<String> sql = new ArrayList<String>();
		sql.add(
				"CREATE TABLE `jails` (" +
						"`ID` VARCHAR(30) NOT NULL," +
						"`x` DOUBLE NOT NULL," +
						"`y` DOUBLE NOT NULL," +
						"`z` DOUBLE NOT NULL," +
						"`world` VARCHAR(50) NOT NULL," +
						"PRIMARY KEY (`ID`)" +
						")"
		);
		queries.put(1, sql);
		return queries;
	}

	public HashMap<String, RunsafeLocation> getJails()
	{
		PreparedStatement select = database.prepare("SELECT ID, x, y, z, world FROM jails");
		HashMap<String, RunsafeLocation> jails = new HashMap<String, RunsafeLocation>();

		try
		{
			ResultSet results = select.executeQuery();
			while (results.next())
			{
				jails.put(results.getString("ID"), new RunsafeLocation(
						new RunsafeWorld(results.getString("world")),
						results.getDouble("x"),
						results.getDouble("y"),
						results.getDouble("z")
				));
			}
		}
		catch (SQLException e)
		{
			this.console.write(e.getMessage());
		}
		return jails;
	}

	private IDatabase database;
	private IOutput console;
}
