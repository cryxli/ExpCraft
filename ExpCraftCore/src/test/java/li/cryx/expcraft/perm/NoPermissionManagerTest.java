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
package li.cryx.expcraft.perm;

import org.bukkit.entity.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test for the fallback permission manager.
 * 
 * @author cryxli
 */
public class NoPermissionManagerTest {

	private static final NoPermissionManager perm = new NoPermissionManager();

	/**
	 * Test for
	 * {@link AbstractPermissionManager#hasAdminCommand(Player, String) 
	 */
	@Test
	public void hasAdminCommand() {
		// player
		Player player = Mockito.mock(Player.class);
		Assert.assertFalse(perm.hasAdminCommand(player, null));

		// op
		Mockito.when(player.isOp()).thenReturn(true);
		Assert.assertTrue(perm.hasAdminCommand(player, null));
	}

	/**
	 * Test for
	 * {@link AbstractPermissionManager#hasLevel(li.cryx.expcraft.module.ExpCraftModule, Player) 
	 */
	@Test
	public void hasLevel() {
		// player
		Player player = Mockito.mock(Player.class);
		Assert.assertTrue(perm.hasLevel(null, player));

		// op
		Mockito.when(player.isOp()).thenReturn(true);
		Assert.assertTrue(perm.hasLevel(null, player));
	}
}
