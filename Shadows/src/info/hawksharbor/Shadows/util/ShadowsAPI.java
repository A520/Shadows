package info.hawksharbor.Shadows.util;

import info.hawksharbor.Shadows.Shadows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.command.CommandSender;

public class ShadowsAPI
{

	private Shadows shadows;

	private static ShadowsChatty chatty;
	private static ShadowsConfigs confManager;
	private static ShadowsLocale localeManager;
	private static boolean latestVersion;

	public ShadowsAPI(Shadows shad)
	{
		shadows = shad;
		chatty = new ShadowsChatty(shadows);
		confManager = new ShadowsConfigs(shadows);
		localeManager = new ShadowsLocale(shadows);
	}

	public String getVersion()
	{
		return Shadows.v;
	}

	public static ArrayList<String> getVanished()
	{
		return Shadows.vanished;
	}

	public static void addVanished(String string)
	{
		Shadows.vanished.add(string);
	}

	public static void remVanished(String string)
	{
		Shadows.vanished.remove(string);
	}

	public static ArrayList<String> getSentInvMsg()
	{
		return Shadows.sentInvisibleMessage;
	}

	public static void addSIM(String string)
	{
		Shadows.sentInvisibleMessage.add(string);
	}

	public static void remSIM(String string)
	{
		Shadows.sentInvisibleMessage.remove(string);
	}

	public static ArrayList<String> getSentVisMsg()
	{
		return Shadows.sentVisibleMessage;
	}

	public static void addSVM(String string)
	{
		Shadows.sentVisibleMessage.add(string);
	}

	public static void remSVM(String string)
	{
		Shadows.sentVisibleMessage.remove(string);
	}

	public static ArrayList<String> getSentDmgMsg()
	{
		return Shadows.sentDamagedMessage;
	}

	public static void addSDM(String string)
	{
		Shadows.sentDamagedMessage.add(string);
	}

	public static void remSDM(String string)
	{
		Shadows.sentDamagedMessage.remove(string);
	}

	public static HashMap<String, Long> getVanishDamaged()
	{
		return Shadows.vDamaged;
	}

	public static void addVD(String string, Long number)
	{
		Shadows.vDamaged.put(string, number);
	}

	public static void remVD(String string)
	{
		Shadows.vDamaged.remove(string);
	}

	public static boolean hasPermission(CommandSender sender, String node)
	{
		if (sender.hasPermission(node))
		{
			if (getConfigManager().getProperty(ShadowsConfFile.SETTINGS,
					"debugMode") != null
					&& "true".equalsIgnoreCase(getConfigManager().getProperty(
							ShadowsConfFile.SETTINGS, "debugMode")))
			{
				String message = getLocaleManager().getString(
						"DebugHasPermission");
				if (message != null)
				{
					message = message.replace("%P", sender.getName()).replace(
							"%D", node);
					sender.sendMessage(message);
				}
			}
			return true;
		}
		if (getConfigManager().getProperty(ShadowsConfFile.SETTINGS,
				"debugMode") != null
				&& "true".equalsIgnoreCase(getConfigManager().getProperty(
						ShadowsConfFile.SETTINGS, "debugMode")))
		{
			String message = getLocaleManager().getString(
					"DebugLacksPermission");
			if (message != null)
			{
				message = message.replace("%P", sender.getName()).replace("%D",
						node);
				sender.sendMessage(message);
			}
		}
		return false;
	}

	public static ShadowsChatty getChatty()
	{
		return chatty;
	}

	public static ShadowsConfigs getConfigManager()
	{
		return confManager;
	}

	public static ShadowsLocale getLocaleManager()
	{
		return localeManager;
	}

	public static int getVanishLightLevel()
	{
		String level = getConfigManager().getProperty(ShadowsConfFile.SETTINGS,
				"vanishLightLevel");
		int vLL;
		try
		{
			vLL = Integer.parseInt(level);
		}
		catch (NumberFormatException e)
		{
			vLL = 5;
			getConfigManager().setProperty(ShadowsConfFile.SETTINGS,
					"vanishLightLevel", vLL);
		}
		return vLL;
	}

	public static void setVanishLightLevel(int i)
	{
		getConfigManager().setProperty(ShadowsConfFile.SETTINGS,
				"vanishLightLevel", i);
	}

	public static void setVanishLightLevel(String string)
	{
		String level = string;
		int vLL;
		try
		{
			vLL = Integer.parseInt(level);
		}
		catch (NumberFormatException e)
		{
			vLL = 5;
		}
		getConfigManager().setProperty(ShadowsConfFile.SETTINGS,
				"vanishLightLevel", vLL);
	}

	public static boolean getVerboseMode()
	{
		String mode = getConfigManager().getProperty(ShadowsConfFile.SETTINGS,
				"verboseMode");
		return Boolean.parseBoolean(mode);
	}

	public static void setVerboseMode(boolean b)
	{
		getConfigManager().setProperty(ShadowsConfFile.SETTINGS, "verboseMode",
				b);
	}

	public static void setVerboseMode(String s)
	{
		getConfigManager().setProperty(ShadowsConfFile.SETTINGS, "verboseMode",
				Boolean.parseBoolean(s));
	}

	public static boolean getDebugMode()
	{
		String mode = getConfigManager().getProperty(ShadowsConfFile.SETTINGS,
				"debugMode");
		return Boolean.parseBoolean(mode);
	}

	public static void setDebugMode(boolean b)
	{
		getConfigManager()
				.setProperty(ShadowsConfFile.SETTINGS, "debugMode", b);
	}

	public static void setDebugMode(String s)
	{
		getConfigManager().setProperty(ShadowsConfFile.SETTINGS, "debugMode",
				Boolean.parseBoolean(s));
	}

	public static String getLocale()
	{
		return getConfigManager().getProperty(ShadowsConfFile.SETTINGS,
				"locale");
	}

	public static void setLocale(String string)
	{
		getConfigManager().setProperty(ShadowsConfFile.SETTINGS, "locale",
				string);
	}

	public static boolean getAlertNewDevBuild()
	{
		String s = getConfigManager().getProperty(ShadowsConfFile.SETTINGS,
				"alertNewDevBuild");
		return Boolean.parseBoolean(s);
	}

	public static void setAlertNewDevBuild(String s)
	{
		getConfigManager().setProperty(ShadowsConfFile.SETTINGS,
				"alertNewDevBuild", Boolean.parseBoolean(s));
	}

	public static void setAlertNewDevBuild(boolean b)
	{
		getConfigManager().setProperty(ShadowsConfFile.SETTINGS,
				"alertNewDevBuild", b);
	}

	public static void checkNewBuild()
	{
		String alert = getConfigManager().getProperty(ShadowsConfFile.SETTINGS,
				"alertNewDevBuild");
		if (alert != null && alert.equalsIgnoreCase("true"))
		{
			checkForNewUpdate();
		}
	}

	private static void checkForNewUpdate()
	{
		String alert = getConfigManager().getProperty(ShadowsConfFile.SETTINGS,
				"alertNewDevBuild");
		if (alert == null || (alert != null && !alert.equalsIgnoreCase("true")))
		{
			return;
		}
		if (!alert.equalsIgnoreCase("true"))
		{
			return;
		}
		latestVersion = true;
		String rawVersion = fetchDevBuildVersion();
		int start = rawVersion.indexOf(">") + 1;
		int end = rawVersion.indexOf("<", start);
		String cookedVersion = rawVersion.substring(start, end);
		if (!cookedVersion.equalsIgnoreCase(Shadows.v))
		{
			ShadowsAPI
					.getChatty()
					.logInfo(
							"New version available at https://topplethenun.ci.cloudbees.com/job/Shadows/");
			latestVersion = false;
		}
	}

	private static String fetchDevBuildVersion()
	{
		BufferedReader in = null;
		Writer writer = new StringWriter();
		try
		{
			URL oracle = new URL(
					"https://topplethenun.ci.cloudbees.com/job/Shadows/lastSuccessfulBuild/api/xml?xpath=freeStyleBuild/number");
			in = new BufferedReader(new InputStreamReader(oracle.openStream(),
					"UTF-8"));

			char[] buffer = new char[1024];
			int n;
			while ((n = in.read(buffer)) != -1)
			{
				writer.write(buffer, 0, n);
			}

		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return writer.toString();
	}

	public static boolean isLatestVersion()
	{
		checkNewBuild();
		return latestVersion;
	}

}
