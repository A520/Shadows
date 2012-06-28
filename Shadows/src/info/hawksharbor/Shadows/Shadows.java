package info.hawksharbor.Shadows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Shadows extends JavaPlugin
{
	private static final Logger log = Logger.getLogger("Minecraft");
	private static ArrayList<String> vanished = new ArrayList<String>();
	private static ArrayList<String> sentInvisibleMessage = new ArrayList<String>();
	private static ArrayList<String> sentVisibleMessage = new ArrayList<String>();
	private static ArrayList<String> sentDamagedMessage = new ArrayList<String>();
	private static HashMap<String, Long> vDamaged = new HashMap<String, Long>();

	@Override
	public void onEnable()
	{
		vanished.clear();
		sentInvisibleMessage.clear();
		sentVisibleMessage.clear();
		sentDamagedMessage.clear();
		vDamaged.clear();
		new ShadowsPlayerListener(this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this,
				new ShadowsRunnable(this), 0, 1);
		log.info("[Shadows]: v" + this.getDescription().getVersion()
				+ " has been loaded.");
	}

	@Override
	public void onDisable()
	{
		log.info("[Shadows]: Disabled.");
	}

	public Logger getLog()
	{
		return log;
	}

	public static ArrayList<String> getVanished()
	{
		return vanished;
	}

	public static void addVanished(String string)
	{
		vanished.add(string);
	}

	public static void remVanished(String string)
	{
		vanished.remove(string);
	}

	public static ArrayList<String> getSentInvMsg()
	{
		return sentInvisibleMessage;
	}

	public static void addSIM(String string)
	{
		sentInvisibleMessage.add(string);
	}

	public static void remSIM(String string)
	{
		sentInvisibleMessage.remove(string);
	}

	public static ArrayList<String> getSentVisMsg()
	{
		return sentVisibleMessage;
	}

	public static void addSVM(String string)
	{
		sentVisibleMessage.add(string);
	}

	public static void remSVM(String string)
	{
		sentVisibleMessage.remove(string);
	}

	public static ArrayList<String> getSentDmgMsg()
	{
		return sentDamagedMessage;
	}

	public static void addSDM(String string)
	{
		sentDamagedMessage.add(string);
	}

	public static void remSDM(String string)
	{
		sentDamagedMessage.remove(string);
	}

	public static HashMap<String, Long> getVanishDamaged()
	{
		return vDamaged;
	}

	public static void addVD(String string, Long number)
	{
		vDamaged.put(string, number);
	}

	public static void remVD(String string)
	{
		vDamaged.remove(string);
	}
}
