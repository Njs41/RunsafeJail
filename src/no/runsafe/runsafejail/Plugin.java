package no.runsafe.runsafejail;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.runsafejail.commands.JailCommand;
import no.runsafe.runsafejail.database.JailedPlayersDatabase;
import no.runsafe.runsafejail.database.JailsDatabase;
import no.runsafe.runsafejail.events.PlayerLoginEvent;
import no.runsafe.runsafejail.events.PlayerMoveEvent;
import no.runsafe.runsafejail.events.PlayerTeleportEvent;
import no.runsafe.runsafejail.handlers.JailHandler;
import no.runsafe.runsafejail.handlers.JailSentenceFactory;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void PluginSetup()
	{
		// TODO: Un-jail command
		// TODO: in-game jail creator
		// TODO: Permission checking.
		// TODO: Exempt check

		// Commands
		this.addComponent(JailCommand.class);

		// Handlers
		this.addComponent(JailHandler.class);

		// Factories
		this.addComponent(JailSentenceFactory.class);

		// Database
		this.addComponent(JailsDatabase.class);
		this.addComponent(JailedPlayersDatabase.class);

		// Events
		this.addComponent(PlayerLoginEvent.class);
		this.addComponent(PlayerMoveEvent.class);
		this.addComponent(PlayerTeleportEvent.class);
	}
}
