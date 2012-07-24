package info.hawksharbor.Shadows;

import info.hawksharbor.Shadows.util.ShadowsAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ShadowsRunnable implements Runnable
{

	private Shadows plugin;

	public ShadowsRunnable(Shadows instance)
	{
		plugin = instance;
	}

	@Override
	public void run()
	{
		for (int x = 0; x < plugin.getServer().getOnlinePlayers().length;)
		{
			Player pl = plugin.getServer().getOnlinePlayers()[x];
			String pn = pl.getName();
			Location loc = pl.getLocation();
			World w = loc.getWorld();
			Block b = w.getBlockAt(loc);
			if (!ShadowsAPI.hasPermission(pl, "shadows.user.disappear"))
			{
				return;
			}
			if (b.getLightLevel() <= ShadowsAPI.getVanishLightLevel()
					&& !ShadowsAPI.getVanished().contains(pn)
					&& !ShadowsAPI.getVanishDamaged().containsKey(pn))
			{
				vanishPlayer(pl);
			}
			else if (b.getLightLevel() > ShadowsAPI.getVanishLightLevel()
					&& ShadowsAPI.getVanished().contains(pn))
			{
				reappearPlayer(pl);
			}
			if (ShadowsAPI.getVanishDamaged().containsKey(pn))
			{
				long cTime = System.currentTimeMillis();
				long pTime = ShadowsAPI.getVanishDamaged().get(pn);
				if ((cTime - pTime) >= 5000)
				{
					ShadowsAPI.remVD(pn);
					ShadowsAPI.remSDM(pn);
					if (b.getLightLevel() <= ShadowsAPI.getVanishLightLevel())
					{
						vanishPlayer(pl);
					}
				}
			}
			return;
		}
	}

	private void reappearPlayer(Player p)
	{
		ShadowsAPI.remVanished(p.getName());
		ShadowsAPI.remSIM(p.getName());
		for (Player other : Bukkit.getServer().getOnlinePlayers())
		{
			if (!other.equals(p) && !other.canSee(p))
			{
				other.showPlayer(p);
			}
		}
		if (!ShadowsAPI.getSentVisMsg().contains(p.getName())
				&& ShadowsAPI.getVerboseMode())
		{
			String message = ShadowsAPI.getLocaleManager().getString("Appear");
			if (message != null)
				p.sendMessage(ChatColor.DARK_GRAY + "[Shadows] " + message);
			ShadowsAPI.addSVM(p.getName());
		}
		return;
	}

	private void vanishPlayer(Player p)
	{
		ShadowsAPI.addVanished(p.getName());
		ShadowsAPI.remSVM(p.getName());
		for (Player other : Bukkit.getServer().getOnlinePlayers())
		{
			if (!other.equals(p) && other.canSee(p))
			{
				if (!ShadowsAPI.hasPermission(other, "shadows.admin.see"))
				{
					other.hidePlayer(p);
				}
			}
		}
		if (!ShadowsAPI.getSentInvMsg().contains(p.getName())
				&& ShadowsAPI.getVerboseMode())
		{
			String message = ShadowsAPI.getLocaleManager().getString("Vanish");
			if (message != null)
				p.sendMessage(ChatColor.DARK_GRAY + "[Shadows] " + message);
			ShadowsAPI.addSIM(p.getName());
		}
		return;
	}

}
