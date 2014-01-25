package no.runsafe.runsafejail.database;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.database.IRow;
import no.runsafe.framework.api.database.ISchemaUpdate;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.database.SchemaUpdate;
import no.runsafe.runsafejail.objects.JailSentence;
import no.runsafe.runsafejail.objects.JailedPlayer;

import javax.annotation.Nullable;
import java.util.List;

public class JailedPlayersDatabase extends Repository
{
	@Override
	public String getTableName()
	{
		return "jailed_players";
	}

	@Override
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate schema = new SchemaUpdate();
		schema.addQueries(
			"CREATE TABLE `jailed_players` (" +
				"`playerName` VARCHAR(20) NOT NULL," +
				"`jailName` VARCHAR(30) NOT NULL," +
				"`sentenceEnd` DATETIME NOT NULL," +
				"`returnX` DOUBLE NOT NULL," +
				"`returnY` DOUBLE NOT NULL," +
				"`returnZ` DOUBLE NOT NULL," +
				"`returnWorld` VARCHAR(50) NULL," +
				"PRIMARY KEY (`playerName`)" +
				")"
		);
		return schema;
	}

	public List<JailedPlayerDatabaseObject> getJailedPlayers()
	{
		return Lists.transform(
			this.database.query(
				"SELECT playerName, jailName, sentenceEnd, returnX, returnY, returnZ, returnWorld FROM jailed_players"
			),
			new Function<IRow, JailedPlayerDatabaseObject>()
			{
				@Override
				public JailedPlayerDatabaseObject apply(@Nullable IRow sentence)
				{
					assert sentence != null;
					JailedPlayerDatabaseObject jailedPlayer = new JailedPlayerDatabaseObject();
					jailedPlayer.setPlayerName(sentence.String("playerName"));
					jailedPlayer.setJailName(sentence.String("jailName"));
					jailedPlayer.setSentenceEnd(sentence.DateTime("sentenceEnd"));
					jailedPlayer.setReturnX(sentence.Double("returnX"));
					jailedPlayer.setReturnY(sentence.Double("returnY"));
					jailedPlayer.setReturnZ(sentence.Double("returnZ"));
					jailedPlayer.setReturnWorld(sentence.String("returnWorld"));
					return jailedPlayer;
				}
			}
		);
	}

	public void addJailedPlayer(JailedPlayer player, JailSentence jailSentence)
	{
		ILocation returnLocation = player.getReturnLocation();
		database.execute(
			"INSERT INTO jailed_players (" +
				"playerName," +
				"jailName," +
				"sentenceEnd," +
				"returnX," +
				"returnY," +
				"returnZ," +
				"returnWorld" +
				") VALUES(?, ?, ?, ?, ?, ?, ?)",
			player.getName(),
			jailSentence.getJailName(),
			jailSentence.getEndSentence(),
			returnLocation.getX(),
			returnLocation.getY(),
			returnLocation.getZ(),
			returnLocation.getWorld()

		);
	}

	public void removeJailedPlayer(String playerName)
	{
		database.execute("DELETE FROM jailed_players WHERE playerName = ?", playerName);
	}
}