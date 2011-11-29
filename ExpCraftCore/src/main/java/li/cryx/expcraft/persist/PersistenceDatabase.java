package li.cryx.expcraft.persist;

import java.text.MessageFormat;

public class PersistenceDatabase extends InMemoryPersistentManager {

	private static final String CREATE_TABLE = //
	"CREATE TABLE ExpCraftTable (" //
			+ " id BIGINT," //
			+ " player VARCHAR(100)," //
			+ " module VARCHAR(5)," //
			+ " exp NUMERIC(8,2)," //
			+ " PRIMARY KEY (id)" //
			+ " )";

	// order: module, player, exp, id
	private static final String INSERT = "INSERT INTO ExpCraftTable (id,module,player,exp) SELECT COALESCE(MAX(id)+1,1),''{0}'',''{1}'',{2} FROM ExpCraftTable";

	// order: module, player
	private static final String SELECT = "SELECT exp FROM ExpCraftTable WHERE module=''{0}'' AND player=''{1}''";

	// order: module, player, exp
	private static final String UPDATE = "UPDATE ExpCraftTable SET exp={2} WHERE module=''{0}'' AND player=''{1}''";

	private static final String KEEP_ALIVE = "SELECT COUNT(*) FROM ExpCraftTable";

	public static void main(final String[] args) {
		System.out.println(CREATE_TABLE);
		System.out.println(MessageFormat.format(SELECT, "Fm", "ups"));
		System.out.println(MessageFormat.format(INSERT, "Fm", "ups", 19.5, 1));
		System.out.println(MessageFormat.format(UPDATE, "Fm", "ups", 22.0));
		System.out.println(KEEP_ALIVE);
	}
}
