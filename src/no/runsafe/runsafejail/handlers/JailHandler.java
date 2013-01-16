package no.runsafe.runsafejail.handlers;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IConfigurationChanged;
import no.runsafe.framework.output.IOutput;
import no.runsafe.runsafejail.Jail;
import no.runsafe.runsafejail.database.JailsDatabase;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Set;

public class JailHandler implements IConfigurationChanged
{
	public JailHandler(JailsDatabase jailsDatabase)
	{
		this.jailsDatabase = jailsDatabase;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.jails = this.jailsDatabase.getJails();
	}

	public Jail getJailByName(String jailName)
	{
		return (this.jails.containsKey(jailName) ? this.jails.get(jailName) : null);
	}

	private HashMap<String, Jail> jails;
	private JailsDatabase jailsDatabase;
}
