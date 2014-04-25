package li.cryx.expcraft.i18n;

import java.util.Locale;

import li.cryx.expcraft.module.ExpCraftModule;

import org.bukkit.command.CommandSender;

public abstract class AbstractTranslator {

	public abstract Locale getLanguage(CommandSender sender);

	public abstract boolean isMultiLingual();

	public abstract void sendMessage(CommandSender sender, final String msgKey,
			final Object... arguments);

	public String translate(final CommandSender sender,
			final ExpCraftModule module) {
		Locale locale = getLanguage(sender);
		return module.getTranslatedName(locale);
	}

	public abstract String translate(final CommandSender sender,
			final String msgKey, final Object... arguments);

}
