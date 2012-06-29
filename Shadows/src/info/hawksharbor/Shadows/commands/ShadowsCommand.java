package info.hawksharbor.Shadows.commands;

import info.hawksharbor.Shadows.Shadows;
import info.hawksharbor.Shadows.util.ShadowsAPI;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;

public class ShadowsCommand implements CommandExecutor
{

	@SuppressWarnings("unused")
	private Shadows _plugin;

	public ShadowsCommand(Shadows plugin)
	{
		_plugin = plugin;
	}

	@EventHandler
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args)
	{
		switch (args.length)
		{
		case 0:
			sender.sendMessage(ChatColor.DARK_GRAY + "--------------------");
			sender.sendMessage(ChatColor.DARK_GRAY + "-----" + ChatColor.GRAY
					+ "Shadows" + ChatColor.DARK_GRAY + "------");
			if (ShadowsAPI.hasPermission(sender, "shadows.admin.info"))
			{
				String debug = String.valueOf(ShadowsAPI.getDebugMode());
				String verbose = String.valueOf(ShadowsAPI.getVerboseMode());
				String vanishLevel = String.valueOf(ShadowsAPI
						.getVanishLightLevel());
				String message = ShadowsAPI.getLocaleManager()
						.getString("Info");
				sender.sendMessage("- "
						+ message.replace("%D", debug).replace("%V", verbose)
								.replace("%I", vanishLevel));
			}
			if (ShadowsAPI.hasPermission(sender, "shadows.command.verbose"))
			{
				String message = ShadowsAPI.getLocaleManager()
						.getString("Help");
				if (message != null)
					sender.sendMessage(message.replace("%C",
							"shadows verbose <true|false>").replace("%D",
							"Changes verboseMode"));
			}
			if (ShadowsAPI.hasPermission(sender, "shadows.command.debug"))
			{
				String message = ShadowsAPI.getLocaleManager()
						.getString("Help");
				if (message != null)
					sender.sendMessage(message.replace("%C",
							"shadows debug <true|false>").replace("%D",
							"Changes debugMode"));
			}
			if (ShadowsAPI.hasPermission(sender, "shadows.command.light"))
			{
				String message = ShadowsAPI.getLocaleManager()
						.getString("Help");
				if (message != null)
					sender.sendMessage(message.replace("%C",
							"shadows light <0-15>").replace("%D",
							"Changes vanishLightLevel"));
			}
			sender.sendMessage(ChatColor.DARK_GRAY + "--------------------");
			return true;
		case 2:
			if (args[0].equalsIgnoreCase("verbose"))
			{
				if (!ShadowsAPI
						.hasPermission(sender, "shadows.command.verbose"))
				{
					String message = ShadowsAPI.getLocaleManager().getString(
							"Unable");
					if (message != null)
						sender.sendMessage(ChatColor.DARK_GRAY + "[Shadows] "
								+ message);
				}
				String booleanTest = args[1];
				boolean b = Boolean.parseBoolean(booleanTest);
				ShadowsAPI.setVerboseMode(b);
				String message = ShadowsAPI.getLocaleManager().getString(
						"Change");
				if (message != null)
					sender.sendMessage(ChatColor.DARK_GRAY
							+ "[Shadows] "
							+ message.replace("%O", "verboseMode").replace(
									"%N", String.valueOf(b)));
				return true;
			}
			else if (args[0].equalsIgnoreCase("debug"))
			{
				if (!ShadowsAPI.hasPermission(sender, "shadows.command.debug"))
				{
					String message = ShadowsAPI.getLocaleManager().getString(
							"Unable");
					if (message != null)
						sender.sendMessage(ChatColor.DARK_GRAY + "[Shadows] "
								+ message);
				}
				String booleanTest = args[1];
				boolean b = Boolean.parseBoolean(booleanTest);
				ShadowsAPI.setDebugMode(b);
				String message = ShadowsAPI.getLocaleManager().getString(
						"Change");
				if (message != null)
					sender.sendMessage(ChatColor.DARK_GRAY
							+ "[Shadows] "
							+ message.replace("%O", "debugMode").replace("%N",
									String.valueOf(b)));
				return true;
			}
			else if (args[0].equalsIgnoreCase("light"))
			{
				if (!ShadowsAPI.hasPermission(sender, "shadows.command.light"))
				{
					String message = ShadowsAPI.getLocaleManager().getString(
							"Unable");
					if (message != null)
						sender.sendMessage(ChatColor.DARK_GRAY + "[Shadows] "
								+ message);
				}
				String intTest = args[1];
				int test;
				try
				{
					test = Integer.parseInt(intTest);
				}
				catch (NumberFormatException e)
				{
					test = 5;
				}
				ShadowsAPI.setVanishLightLevel(test);
				String message = ShadowsAPI.getLocaleManager().getString(
						"Change");
				if (message != null)
					sender.sendMessage(ChatColor.DARK_GRAY
							+ "[Shadows] "
							+ message.replace("%O", "vanishLightLevel")
									.replace("%N", String.valueOf(test)));
				return true;
			}
		default:
			String message = ShadowsAPI.getLocaleManager().getString("Unable");
			if (message != null)
				sender.sendMessage(ChatColor.DARK_GRAY + "[Shadows] " + message);
			return true;
		}
	}
}
