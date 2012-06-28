package info.hawksharbor.Shadows;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ShadowsPlayerListener implements Listener
{

	public ShadowsPlayerListener(Shadows plugin)
	{
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		updateVanishState(event.getPlayer());
	}

	private void updateVanishState(Player player)
	{
		String playerName = player.getName();
		Server server = Bukkit.getServer();
		if (Shadows.getVanished().contains(playerName))
		{
			vanishPlayer(player);
		}
		else
		{
			for (Player other : server.getOnlinePlayers())
			{
				if (!other.canSee(player))
				{
					other.showPlayer(player);
				}
			}
		}
		if (!player.hasPermission("shadows.see"))
		{
			for (String name : Shadows.getVanished())
			{
				if (name.equals(playerName))
					continue;
				Player other = server.getPlayerExact(name);
				if (other != null)
				{
					if (player.canSee(other))
					{
						player.hidePlayer(other);
					}
				}
			}
		}
		else
		{
			for (String name : Shadows.getVanished())
			{
				if (name.equals(playerName))
					continue;
				Player other = server.getPlayerExact(name);
				if (other != null)
				{
					if (!player.canSee(other))
					{
						player.showPlayer(other);
					}
				}
			}
		}
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
			p.sendMessage(ChatColor.DARK_GRAY + "[Shadows] " + ChatColor.GRAY
					+ "You are now invisible.");
			Shadows.addSIM(p.getName());
		}
		return;
	}

	@EventHandler
	public void onEntityDamageOtherEntity(EntityDamageByEntityEvent event)
	{
		Entity damager = event.getDamager();
		EntityType damagerType = damager.getType();
		String damagerName = damagerType.toString();
		if (!damagerType.equals(EntityType.PLAYER))
		{
			return;
		}
		Player pl = ((Player) damager);
		if (pl.hasPermission("shadows.sneakattack.invisible")
				|| pl.hasPermission("shadows.sneakattack.visible"))
		{
			int dam = event.getDamage();
			event.setDamage(dam *= 2);
			if (pl.hasPermission("shadows.sneakattack.invisible"))
				return;
		}
		damagerName = pl.getName();
		long dTime = System.currentTimeMillis();
		Shadows.addVD(damagerName, dTime);
		if (!Shadows.getSentDmgMsg().contains(damagerName)
				&& Shadows.getVanished().contains(damagerName))
		{
			((Player) damager).sendMessage(ChatColor.DARK_GRAY + "[Shadows] "
					+ ChatColor.GRAY + "You have been revealed.");
			Shadows.addSDM(damagerName);
		}
		Shadows.remVanished(damagerName);
		Shadows.remSIM(damagerName);
		for (Player other : Bukkit.getServer().getOnlinePlayers())
		{
			if (!other.equals(damagerName) && !other.canSee((Player) damager))
			{
				other.showPlayer(((Player) damager));
			}
		}
		return;
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		Entity ent = event.getEntity();
		if (!(ent instanceof Player))
		{
			return;
		}
		Player p = (Player) ent;
		if (p.hasPermission("shadows.immunity"))
		{
			event.setCancelled(true);
		}
		String pn = p.getName();
		if (!Shadows.getSentDmgMsg().contains(pn)
				&& Shadows.getVanished().contains(pn))
		{
			p.sendMessage(ChatColor.DARK_GRAY + "[Shadows] " + ChatColor.GRAY
					+ "You have been revealed.");
			Shadows.addSDM(pn);
			Shadows.remVanished(pn);
		}
		long dTime = System.currentTimeMillis();
		Shadows.addVD(pn, dTime);
		Shadows.remSIM(pn);
		for (Player other : Bukkit.getServer().getOnlinePlayers())
		{
			if (!other.equals(p) && !other.canSee(p))
			{
				other.showPlayer(p);
			}
		}
		return;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityTarget(EntityTargetEvent event)
	{
		if (event.isCancelled())
		{
			return;
		}
		Entity target = event.getTarget();
		if (target instanceof Player)
		{
			String playerName = ((Player) target).getName();
			if (Shadows.getVanished().contains(playerName))
			{
				event.setCancelled(true);
			}
		}
	}
}
