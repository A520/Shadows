package info.hawksharbor.Shadows;

import info.hawksharbor.Shadows.commands.ShadowsCommand;
import info.hawksharbor.Shadows.util.ShadowsAPI;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;

public class Shadows extends JavaPlugin
{
	public static ArrayList<String> vanished = new ArrayList<String>();
	public static ArrayList<String> sentInvisibleMessage = new ArrayList<String>();
	public static ArrayList<String> sentVisibleMessage = new ArrayList<String>();
	public static ArrayList<String> sentDamagedMessage = new ArrayList<String>();
	public static HashMap<String, Long> vDamaged = new HashMap<String, Long>();
	public static String v;

	private ShadowsAPI apiManager;
	private ShadowsCommand shadEx;

	@Override
	public void onEnable()
	{
		v = this.getDescription().getVersion();
		vanished.clear();
		sentInvisibleMessage.clear();
		sentVisibleMessage.clear();
		sentDamagedMessage.clear();
		vDamaged.clear();
		shadEx = new ShadowsCommand(this);
		getCommand("shadows").setExecutor(shadEx);
		new ShadowsAPI(this);
		new ShadowsPlayerListener(this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this,
				new ShadowsRunnable(this), 0, 1);
		ShadowsAPI.getChatty().logInfo("v" + v + " enabled.");
	}

	@Override
	public void onDisable()
	{
		ShadowsAPI.getChatty().logInfo("Disabled.");
	}

	public ShadowsAPI getAPIManager()
	{
		return apiManager;
	}
}
