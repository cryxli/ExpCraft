package li.cryx.expcraft.farming;

public class Config {

	public class DropLevel {
		public int cocoaBean = 70;
		public int melonSeed = 40;
		public int pumpkinSeed = 20;
	}

	public class Exp {
		public double till = 1;
		public double harvest = 5;
		public double sugarCane = 2;
		public double apple = 3;
		public double goldenApple = 100;
		public double cactus = 3;
		public double sapling = 0.5;
		public double redRose = 0.1;
		public double yellowFlower = 0.1;
		public double mushroom = 0.1;
		public double wheat = 2;
	}

	public class Level {
		public int till = 0;
		public int harvest = 0;
		public int sugarCane = 0;
		public int apple = 0;
		public int goldenApple = 0;
		public int cactus = 0;
		public int sapling = 0;
		public int redRose = 0;
		public int yellowFlower = 0;
		public int mushroom = 0;
		public int wheat = 0;
	}

	public class ToolLevel {
		public int wood = 0;
		public int stone = 5;
		public int iron = 10;
		public int gold = 20;
		public int diamond = 30;
	}

	public final Level LEVEL = new Level();
	public final Exp EXP = new Exp();
	public final DropLevel DROP_LEVEL = new DropLevel();
	public final ToolLevel TOOL_LEVEL = new ToolLevel();

}
