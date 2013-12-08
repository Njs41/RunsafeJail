package no.runsafe.runsafejail.workers;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.event.player.IPlayerLoginEvent;
import no.runsafe.framework.api.event.player.IPlayerMove;
import no.runsafe.framework.api.event.player.IPlayerTeleportEvent;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerLoginEvent;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerTeleportEvent;
import no.runsafe.framework.timer.ForegroundWorker;
import no.runsafe.runsafejail.handlers.JailHandler;

public class TetherWorker extends ForegroundWorker<String, Object> implements IPlayerMove, IPlayerTeleportEvent, IPlayerLoginEvent
{
	public TetherWorker(IScheduler scheduler, IServer server)
	{
		super(scheduler);
		this.server = server;
	}

	public void setJailHandler(JailHandler jailHandler)
	{
		this.jailHandler = jailHandler;
	}

	@Override
	public void process(String playerName, Object object)
	{
		IPlayer player = server.getPlayer(playerName);
		ILocation jailLocation = this.jailHandler.getPlayerJailLocation(player.getName());

		if (jailLocation.distance(player.getLocation()) > this.jailHandler.getJailTether())
			player.teleport(jailLocation);
	}

	@Override
	public void OnPlayerLogin(RunsafePlayerLoginEvent event)
	{
		this.Push(event.getPlayer().getName(), null);
	}

	@Override
	public boolean OnPlayerMove(IPlayer player, ILocation from, ILocation to)
	{
		this.Push(player.getName(), null);
		return true;
	}

	@Override
	public void OnPlayerTeleport(RunsafePlayerTeleportEvent event)
	{
		this.Push(event.getPlayer().getName(), null);
	}

	private JailHandler jailHandler;
	private final IServer server;
}
