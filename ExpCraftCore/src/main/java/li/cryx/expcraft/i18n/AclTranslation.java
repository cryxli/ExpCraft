/*
 * Copyright (c) 2011 Urs P. Simport java.util.Locale;

import li.cryx.minecraft.acl.Language;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
d documentation files (the
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
package li.cryx.expcraft.i18n;

import java.util.Locale;

import li.cryx.minecraft.acl.Language;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Implementation of {@link AbstractTranslator} that delegates the work to the
 * bukkit plugin <code>Language</code>.
 * 
 * @author cryxli
 */
public class AclTranslation extends AbstractTranslator {

	/** Reference to the plugin translator */
	private final Language lng;

	/** Create an instance. */
	public AclTranslation(final JavaPlugin plugin) {
		// create an instance of the translator from the plugin
		lng = Language.getInstanceFor(plugin);
	}

	@Override
	public Locale getLanguage(final CommandSender sender) {
		return Language.getLanguage(sender);
	}

	@Override
	public boolean isMultiLingual() {
		return true;
	}

	@Override
	public void sendMessage(final CommandSender sender, final String msgKey,
			final Object... arguments) {
		lng.sendMessage(sender, msgKey, arguments);
	}

	@Override
	public String translate(final CommandSender sender, final String msgKey,
			final Object... arguments) {
		return lng.translate(sender, msgKey, arguments);
	}

}
