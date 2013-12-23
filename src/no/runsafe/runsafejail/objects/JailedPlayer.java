package no.runsafe.runsafejail.objects;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.player.IPlayer;

public class JailedPlayer
{
	public JailedPlayer(IPlayer player)
	{
		this.player = player;
	}

	public void returnFromJail()
	{
		if (this.returnLocation != null)
			player.teleport(this.returnLocation);
	}

	public void setReturnLocation(ILocation returnLocation)
	{
		this.returnLocation = returnLocation;
	}

	public void setReturnLocation()
	{
		this.returnLocation = player.getLocation();
	}

	public boolean hasReturnLocation()
	{
		return this.returnLocation != null;
	}

	public ILocation getReturnLocation()
	{
		return this.returnLocation;
	}

	public String getName()
	{
		return player.getName();
	}

	public void teleport(ILocation jailLocation)
	{
		player.teleport(jailLocation);
	}

	private ILocation returnLocation;
	private final IPlayer player;
}
