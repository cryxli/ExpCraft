package li.cryx.expcraft;

/**
 * TODO documentation
 * 
 * @author cryxli
 */
public interface CoreConfig {

	String LEVEL_CONST = "Levels.Constant";
	String LEVEL_CAP = "Levels.LevelCap";

	String PERSISTENCE_IMPL = "Database";

	String CHAT_PLAY_SOUND = "Chat.PlayLevelUpSound";
	String CHAT_NOTIFY_ALL = "Chat.NotifyAll";
	String CHAT_COLOR_ONE = "Colors.ColorOne";
	String CHAT_COLOR_TWO = "Colors.ColorTwo";
	String CHAT_COLOR_GOOD = "Colors.ColorGood";
	String CHAT_COLOR_BAD = "Colors.ColorBad";

	/** Comma separated list of world names for which ExpCraft is enabled. */
	String WORLDS = "Worlds";

}
