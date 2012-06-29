package info.hawksharbor.Shadows.util;

import info.hawksharbor.Shadows.Shadows;

public enum ShadowsConfFile
{
	SETTINGS("plugins/Shadows/" + getPluginVersion() + "/Settings.yml"), LOCALE(
			"plugins/Shadows/" + getPluginVersion() + "/Locale.yml");

	private final String _path;

	private ShadowsConfFile(final String path)
	{
		_path = path;
	}

	public String getPath()
	{
		return _path;
	}

	private static String getPluginVersion()
	{
		return "v" + Shadows.v;
	}
}
