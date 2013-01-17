package no.runsafe.runsafejail.database;

import org.joda.time.DateTime;

public class JailedPlayerDatabaseObject
{
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	public void setJailName(String jailName)
	{
		this.jailName = jailName;
	}

	public void setSentenceEnd(DateTime sentenceEnd)
	{
		this.sentenceEnd = sentenceEnd;
	}

	public void setReturnX(double returnX)
	{
		this.returnX = returnX;
	}

	public void setReturnY(double returnY)
	{
		this.returnY = returnY;
	}

	public void setReturnZ(double returnZ)
	{
		this.returnZ = returnZ;
	}

	public void setReturnWorld(String returnWorld)
	{
		this.returnWorld = returnWorld;
	}

	public String getPlayerName()
	{
		return this.playerName;
	}

	public String getJailName()
	{
		return this.jailName;
	}

	public DateTime getSentenceEnd()
	{
		return this.sentenceEnd;
	}

	public double getReturnX()
	{
		return this.returnX;
	}

	public double getReturnY()
	{
		return this.returnY;
	}

	public double getReturnZ()
	{
		return this.returnZ;
	}

	public String getReturnWorld()
	{
		return this.returnWorld;
	}

	private String playerName;
	private String jailName;
	private DateTime sentenceEnd;
	private double returnX;
	private double returnY;
	private double returnZ;
	private String returnWorld;
}
