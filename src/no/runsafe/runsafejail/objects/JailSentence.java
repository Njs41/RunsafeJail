package no.runsafe.runsafejail.objects;

import no.runsafe.framework.api.ITimer;
import org.joda.time.DateTime;

public class JailSentence
{
	public JailSentence(String jailName, DateTime endSentence, ITimer jailTimer)
	{
		this.jailName = jailName;
		this.endSentence = endSentence;
		this.jailTimer = jailTimer;
	}

	public String getJailName()
	{
		return this.jailName;
	}

	public ITimer getJailTimer()
	{
		return this.jailTimer;
	}

	public DateTime getEndSentence()
	{
		return this.endSentence;
	}

	private String jailName;
	private ITimer jailTimer;
	private DateTime endSentence;
}
