package no.runsafe.runsafejail;

import no.runsafe.framework.server.RunsafeLocation;
import no.runsafe.framework.server.RunsafeWorld;
import no.runsafe.framework.server.player.RunsafePlayer;

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

	public void teleportPlayerHere(RunsafePlayer player)
	{
		player.teleport(this.location);
	}

	private RunsafeLocation location;
}
