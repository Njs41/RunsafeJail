package no.runsafe.runsafejail.events;

import no.runsafe.framework.event.player.IPlayerLoginEvent;
import no.runsafe.framework.server.event.player.RunsafePlayerLoginEvent;
import no.runsafe.runsafejail.handlers.JailHandler;

public class PlayerLoginEvent implements IPlayerLoginEvent
{
	public PlayerLoginEvent(JailHandler jailHandler)
	{
		this.jailHandler = jailHandler;
	}

	@Override
	public void OnPlayerLogin(RunsafePlayerLoginEvent event)
	{
		this.jailHandler.checkTether(event.getPlayer());
	}

	private JailHandler jailHandler;
}
