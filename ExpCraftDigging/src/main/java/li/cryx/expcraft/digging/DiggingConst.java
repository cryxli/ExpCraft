/*
 * Copyright (c) 2011 Urs P. Stettler, https://github.com/cryxli
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package li.cryx.expcraft.digging;

/**
 * This interface contains keys used in the modules config file.
 * 
 * @author cryxli
 */
public class DiggingConst {

	/** How many experience is gained when digging up the indicated block. */
	public static final class EXP {
		public static final String DIRT = "ExpGain.Dirt";
		public static final String GRASS = "ExpGain.Grass";
		public static final String SAND = "ExpGain.Sand";
		public static final String CLAY = "ExpGain.Clay";
		public static final String SOULSAND = "ExpGain.SoulSand";
		public static final String GRAVEL = "ExpGain.Gravel";
		public static final String SNOW = "ExpGain.Snow";
		public static final String MYCELIUM = "ExpGain.Mycelium";
	}

	/** What level in digging is required to dig up the indicated block. */
	public static final class LEVEL {
		public static final String DIRT = "UseLevel.Dirt";
		public static final String GRASS = "UseLevel.Grass";
		public static final String SAND = "UseLevel.Sand";
		public static final String CLAY = "UseLevel.Clay";
		public static final String SOULSAND = "UseLevel.SoulSand";
		public static final String GRAVEL = "UseLevel.Gravel";
		public static final String SNOW = "UseLevel.Snow";
		public static final String MYCELIUM = "UseLevel.Mycelium";
	}

	/** Which level in digging is required to use the indicated shovel. */
	public static final class SHOVEL {
		/** Level for wooden shovel. */
		public static final String WOODEN = "ShovelLevel.Wooden";
		/** Level for (cobble)stone shovel. */
		public static final String STONE = "ShovelLevel.Stone";
		/** Level for iron shovel. */
		public static final String IRON = "ShovelLevel.Iron";
		/** Level for golden shovel. */
		public static final String GOLD = "ShovelLevel.Gold";
		/** Level for diamond shovel. */
		public static final String DIAMOND = "ShovelLevel.Diamond";
	}

	public static final String FIRE_SHOVEL_LEVEL = "Settings.FireShovelLevel";
}
