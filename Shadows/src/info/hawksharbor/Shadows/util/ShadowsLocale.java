package info.hawksharbor.Shadows.util;

import info.hawksharbor.Shadows.Shadows;

public class ShadowsLocale
{
	@SuppressWarnings("unused")
	private final Shadows _plugin;

	public ShadowsLocale(Shadows plugin)
	{
		_plugin = plugin;
	}

	public String getString(String key)
	{
		String locale = ShadowsAPI.getConfigManager().getProperty(
				ShadowsConfFile.SETTINGS, "locale");

		if (locale != null)
			locale = locale.toLowerCase();
		else
			locale = "en";

		String value = ShadowsAPI.getConfigManager().getProperty(
				ShadowsConfFile.LOCALE, locale + "." + key);

		if (value == null)
			value = ShadowsAPI.getConfigManager().getProperty(
					ShadowsConfFile.LOCALE, "en." + key);

		if (value != null)
			value = value.replace('&', '\u00A7').replace("\u00A7\u00A7", "&");

		return value;
	}
}
