package info.hawksharbor.Shadows;

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
			if (!pl.hasPermission("shadows.disappear"))
			{
				return;
			}
			if (b.getLightLevel() <= 5 && !Shadows.getVanished().contains(pn)
					&& !Shadows.getVanishDamaged().containsKey(pn))
			{
				vanishPlayer(pl);
			}
			else if (b.getLightLevel() > 5
					&& Shadows.getVanished().contains(pn))
			{
				reappearPlayer(pl);
			}
			if (Shadows.getVanishDamaged().containsKey(pn))
			{
				long cTime = System.currentTimeMillis();
				long pTime = Shadows.getVanishDamaged().get(pn);
				if ((cTime - pTime) >= 5000)
				{
					Shadows.remVD(pn);
					Shadows.remSDM(pn);
					if (b.getLightLevel() <= 5)
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
		Shadows.remVanished(p.getName());
		Shadows.remSIM(p.getName());
		for (Player other : Bukkit.getServer().getOnlinePlayers())
		{
			if (!other.equals(p) && !other.canSee(p))
			{
				other.showPlayer(p);
			}
		}
		if (!Shadows.getSentVisMsg().contains(p.getName()))
		{
			p.sendMessage(ChatColor.DARK_GRAY + "[Shadows]: " + ChatColor.GRAY
					+ "You are now visible.");
			Shadows.addSVM(p.getName());
		}
		return;
	}

	private void vanishPlayer(Player p)
	{
		Shadows.addVanished(p.getName());
		Shadows.remSVM(p.getName());
		for (Player other : Bukkit.getServer().getOnlinePlayers())
		{
			if (!other.equals(p) && other.canSee(p))
			{
				if (!other.hasPermission("shadows.see"))
				{
					other.hidePlayer(p);
				}
			}
		}
		if (!Shadows.getSentInvMsg().contains(p.getName()))
		{
			p.sendMessage(ChatColor.DARK_GRAY + "[Shadows]: " + ChatColor.GRAY
					+ "You are now invisible.");
			Shadows.addSIM(p.getName());
		}
		return;
	}

}
