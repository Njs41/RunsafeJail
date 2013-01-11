package no.runsafe.runsafejail;

import no.runsafe.framework.server.RunsafeLocation;
import no.runsafe.framework.server.RunsafeWorld;

public class Jail
{
	public Jail(double x, double y, double z, String worldName)
	{
		this.location = new RunsafeLocation(new RunsafeWorld(worldName), x, y, z);
	}

	public RunsafeLocation getLocation()
	{
		return this.location;
	}

	private RunsafeLocation location;
}
