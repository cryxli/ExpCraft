package li.cryx.expcraft.i18n;

import java.util.Locale;

import li.cryx.minecraft.acl.Language;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class AclTranslation extends AbstractTranslator {

	private final Language lng;

	public AclTranslation(final JavaPlugin plugin) {
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
