package no.runsafe.runsafejail;

import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.framework.timer.CallbackTimer;
import org.joda.time.DateTime;

public class JailSentence
{
	public JailSentence(String playerName, String jailName, DateTime end)
	{
		this.playerName = playerName;
		this.jailName = jailName;
		this.end = end;
	}

	public RunsafePlayer getPlayer()
	{
		return RunsafeServer.Instance.getPlayer(this.playerName);
	}

	public String getJailName()
	{
		return this.jailName;
	}

	public DateTime getEndTime()
	{
		return this.end;
	}

	public void setSentenceTimer(CallbackTimer timer)
	{
		this.timer = timer;
	}

	private String playerName;
	private String jailName;
	private DateTime end;
	private CallbackTimer timer;
}
