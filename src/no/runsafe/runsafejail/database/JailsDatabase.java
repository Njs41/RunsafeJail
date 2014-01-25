package no.runsafe.runsafejail.database;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.database.IRow;
import no.runsafe.framework.api.database.ISchemaUpdate;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.database.SchemaUpdate;

import java.util.HashMap;

public class JailsDatabase extends Repository
{
	public JailsDatabase(IOutput console)
	{
		this.console = console;
	}

	@Override
	public String getTableName()
	{
		return "jails";
	}

	@Override
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate schema = new SchemaUpdate();
		schema.addQueries(
			"CREATE TABLE `jails` (" +
				"`ID` VARCHAR(30) NOT NULL," +
				"`x` DOUBLE NOT NULL," +
				"`y` DOUBLE NOT NULL," +
				"`z` DOUBLE NOT NULL," +
				"`world` VARCHAR(50) NOT NULL," +
				"PRIMARY KEY (`ID`)" +
				")"
		);
		return schema;
	}

	public HashMap<String, ILocation> getJails()
	{
		HashMap<String, ILocation> jails = new HashMap<String, ILocation>();
		for (IRow jail : database.query("SELECT ID, world, x, y, z FROM jails"))
			jails.put(
				jail.String("ID"),
				jail.Location()
			);
		return jails;
	}

	private IOutput console;
}
