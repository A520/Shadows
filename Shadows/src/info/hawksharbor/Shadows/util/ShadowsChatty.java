package info.hawksharbor.Shadows.util;

import info.hawksharbor.Shadows.Shadows;

import java.util.logging.Logger;

public class ShadowsChatty
{

	private Shadows shadows;

	public static Logger log;

	public ShadowsChatty(Shadows shad)
	{
		shadows = shad;
		log = Logger.getLogger("Minecraft");
		logInfo("Messages are loaded.");
	}

	private String getPluginName()
	{
		return shadows.getDescription().getName();
	}

	private String getLogPrefix()
	{
		return "[" + getPluginName() + "] ";
	}

	private Logger getLog()
	{
		return log;
	}

	public void logInfo(String string)
	{
		getLog().info(getLogPrefix() + string);
	}

	public void logSevere(String string)
	{
		getLog().severe(getLogPrefix() + string);
	}
}
