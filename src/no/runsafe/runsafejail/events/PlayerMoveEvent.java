package no.runsafe.runsafejail.events;

import no.runsafe.framework.event.player.IPlayerMove;
import no.runsafe.framework.server.RunsafeLocation;
import no.runsafe.framework.server.event.player.RunsafePlayerMoveEvent;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.runsafejail.exceptions.JailException;
import no.runsafe.runsafejail.handlers.JailHandler;

public class PlayerMoveEvent implements IPlayerMove
{
	public PlayerMoveEvent(JailHandler jailHandler)
	{
		this.jailHandler = jailHandler;
	}

	@Override
	public boolean OnPlayerMove(RunsafePlayer player, RunsafeLocation from, RunsafeLocation to)
	{
		this.jailHandler.checkTether(player);
		return true;
	}

	private JailHandler jailHandler;
}
