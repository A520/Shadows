package info.hawksharbor.Shadows;

import info.hawksharbor.Shadows.util.ShadowsAPI;
import info.hawksharbor.Shadows.util.ShadowsConfFile;

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
		if (ShadowsAPI.hasPermission(event.getPlayer(), "shadows.admin.alert")
				&& !Shadows.latestVersion)
		{
			if (Boolean.parseBoolean(ShadowsAPI.getConfigManager().getProperty(
					ShadowsConfFile.SETTINGS, "alertNewDevBuild")))
			{
				String message = ShadowsAPI.getLocaleManager().getString(
						"NewVersionAvailable");
				if (message != null)
					event.getPlayer().sendMessage(
							ChatColor.DARK_GRAY + "[Shadows] " + message);
			}
		}
	}

	private void updateVanishState(Player player)
	{
		String playerName = player.getName();
		Server server = Bukkit.getServer();
		if (ShadowsAPI.getVanished().contains(playerName))
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
		if (!ShadowsAPI.hasPermission(player, "shadows.admin.see"))
		{
			for (String name : ShadowsAPI.getVanished())
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
			for (String name : ShadowsAPI.getVanished())
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
		if (!ShadowsAPI.getSentInvMsg().contains(p.getName()))
		{
			String message = ShadowsAPI.getLocaleManager().getString("Vanish");
			if (message != null)
				p.sendMessage(ChatColor.DARK_GRAY + "[Shadows] " + message);
			ShadowsAPI.addSIM(p.getName());
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
		damagerName = pl.getName();
		long dTime = System.currentTimeMillis();
		ShadowsAPI.addVD(damagerName, dTime);
		if (!ShadowsAPI.getSentDmgMsg().contains(damagerName)
				&& ShadowsAPI.getVanished().contains(damagerName))
		{
			String message = ShadowsAPI.getLocaleManager().getString("Reveal");
			if (message != null)
				pl.sendMessage(ChatColor.DARK_GRAY + "[Shadows] " + message);
			ShadowsAPI.addSDM(damagerName);
		}
		ShadowsAPI.remVanished(damagerName);
		ShadowsAPI.remSIM(damagerName);
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
		if (ShadowsAPI.hasPermission(p, "shadows.admin.immunity"))
		{
			event.setCancelled(true);
		}
		String pn = p.getName();
		if (!ShadowsAPI.getSentDmgMsg().contains(pn)
				&& ShadowsAPI.getVanished().contains(pn))
		{
			String message = ShadowsAPI.getLocaleManager().getString("Reveal");
			if (message != null)
				p.sendMessage(ChatColor.DARK_GRAY + "[Shadows] " + message);
			ShadowsAPI.addSDM(pn);
			ShadowsAPI.remVanished(pn);
		}
		long dTime = System.currentTimeMillis();
		ShadowsAPI.addVD(pn, dTime);
		ShadowsAPI.remSIM(pn);
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
			if (ShadowsAPI.getVanished().contains(playerName))
			{
				event.setCancelled(true);
			}
		}
	}
}
