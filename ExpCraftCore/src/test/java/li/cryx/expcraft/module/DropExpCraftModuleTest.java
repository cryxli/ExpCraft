package li.cryx.expcraft.module;

import junit.framework.Assert;

import org.junit.Test;

public class DropExpCraftModuleTest {

	@Test
	public void verifyAmount() {
		for (int i = 0; i <= 5; i++) {
			int amount = i - i / 3 - i / 4;
			switch (i) {
			case 0:
				Assert.assertEquals(0, amount);
				break;
			case 1:
				Assert.assertEquals(1, amount);
				break;
			case 5:
				Assert.assertEquals(3, amount);
				break;
			default:
				Assert.assertEquals(2, amount);
				break;
			}
		}
	}

}