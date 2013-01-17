package no.runsafe.runsafejail.events;

import no.runsafe.framework.event.player.IPlayerTeleportEvent;
import no.runsafe.framework.server.event.player.RunsafePlayerTeleportEvent;
import no.runsafe.runsafejail.handlers.JailHandler;

public class PlayerTeleportEvent implements IPlayerTeleportEvent
{
	public PlayerTeleportEvent(JailHandler jailHandler)
	{
		this.jailHandler = jailHandler;
	}

	@Override
	public void OnPlayerTeleport(RunsafePlayerTeleportEvent event)
	{
		this.jailHandler.checkTether(event.getPlayer());
	}

	private JailHandler jailHandler;
}
