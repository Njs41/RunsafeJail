package no.runsafe.runsafejail.handlers;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IConfigurationChanged;
import no.runsafe.runsafejail.Jail;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Set;

public class JailHandler implements IConfigurationChanged
{
	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		ConfigurationSection configJails = configuration.getSection("jails");
		Set<String> jails = configJails.getKeys(false);
	}

	private HashMap<String, Jail> jails;
}
