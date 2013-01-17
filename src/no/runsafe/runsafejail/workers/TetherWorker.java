package no.runsafe.runsafejail.workers;

import no.runsafe.framework.event.player.IPlayerLoginEvent;
import no.runsafe.framework.event.player.IPlayerMove;
import no.runsafe.framework.event.player.IPlayerTeleportEvent;
import no.runsafe.framework.server.RunsafeLocation;
import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.event.player.RunsafePlayerLoginEvent;
import no.runsafe.framework.server.event.player.RunsafePlayerTeleportEvent;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.framework.timer.ForegroundWorker;
import no.runsafe.framework.timer.IScheduler;
import no.runsafe.runsafejail.handlers.JailHandler;

public class TetherWorker extends ForegroundWorker<String, Object> implements IPlayerMove, IPlayerTeleportEvent, IPlayerLoginEvent
{
	public TetherWorker(IScheduler scheduler)
	{
		super(scheduler);
	}

	public void setJailHandler(JailHandler jailHandler)
	{
		this.jailHandler = jailHandler;
	}

	@Override
	public void process(String playerName, Object object)
	{
		RunsafePlayer player = RunsafeServer.Instance.getPlayer(playerName);
		RunsafeLocation jailLocation = this.jailHandler.getPlayerJailLocation(player.getName());

		if (jailLocation.distance(player.getLocation()) > this.jailHandler.getJailTether())
			player.teleport(jailLocation);
	}

	@Override
	public void OnPlayerLogin(RunsafePlayerLoginEvent event)
	{
		this.Push(event.getPlayer().getName(), null);
	}

	@Override
	public boolean OnPlayerMove(RunsafePlayer player, RunsafeLocation from, RunsafeLocation to)
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
}
