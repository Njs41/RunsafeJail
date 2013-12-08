package no.runsafe.runsafejail.handlers;

import no.runsafe.framework.api.IDebug;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.timer.TimerFactory;
import no.runsafe.runsafejail.objects.JailedPlayer;

public class JailSentenceFactory extends TimerFactory<JailedPlayer>
{
	public JailSentenceFactory(IScheduler scheduler, IDebug console)
	{
		super(scheduler);
		this.console = console;
	}

	public void setJailHandler(JailHandler jailHandler)
	{
		this.jailHandler = jailHandler;
	}

	@Override
	public void OnTimerElapsed(JailedPlayer state)
	{
		state.returnFromJail();
		this.console.debugFine(
			"Jail timer for %s has been killed, triggering sentence removal.",
			state.getName()
		);
		this.jailHandler.removeJailSentence(state.getName());
	}

	private IDebug console;
	private JailHandler jailHandler;
}
