package no.runsafe.runsafejail.database;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.database.IDatabase;
import no.runsafe.framework.api.database.IRow;
import no.runsafe.framework.api.database.Repository;

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

	public HashMap<String, ILocation> getJails()
	{
		HashMap<String, ILocation> jails = new HashMap<String, ILocation>();
		for (IRow jail : database.Query("SELECT ID, world, x, y, z FROM jails"))
			jails.put(
				jail.String("ID"),
				jail.Location()
			);
		return jails;
	}

	private IDatabase database;
	private IOutput console;
}
