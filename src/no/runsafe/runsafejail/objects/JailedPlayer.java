package no.runsafe.runsafejail.objects;

import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import org.bukkit.entity.Player;

public class JailedPlayer extends RunsafePlayer
{
	public JailedPlayer(Player toWrap)
	{
		super(toWrap);
	}

	public void returnFromJail()
	{
		if (this.returnLocation != null)
			this.teleport(this.returnLocation);
	}

	public void setReturnLocation(RunsafeLocation returnLocation)
	{
		this.returnLocation = returnLocation;
	}

	public void setReturnLocation()
	{
		this.returnLocation = this.getLocation();
	}

	public boolean hasReturnLocation()
	{
		return this.returnLocation != null;
	}

	public RunsafeLocation getReturnLocation()
	{
		return this.returnLocation;
	}

	RunsafeLocation returnLocation;
}
