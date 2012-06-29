package info.hawksharbor.Shadows.util;

import info.hawksharbor.Shadows.Shadows;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ShadowsConfigs
{
	@SuppressWarnings("unused")
	private final Shadows _plugin;

	private final HashMap<ShadowsConfFile, YamlConfiguration> _configurations;

	public ShadowsConfigs(Shadows plugin)
	{
		_plugin = plugin;

		_configurations = new HashMap<ShadowsConfFile, YamlConfiguration>();

		this.loadConfig();

		ShadowsAPI.getChatty().logInfo("Configuration is loaded.");
	}

	public void loadConfig()
	{
		for (ShadowsConfFile file : ShadowsConfFile.values())
		{
			File confFile = new File(file.getPath());

			if (confFile.exists())
			{
				if (_configurations.containsKey(file))
					_configurations.remove(file);

				YamlConfiguration conf = YamlConfiguration
						.loadConfiguration(confFile);
				_configurations.put(file, conf);
			}
			else
			{
				File parentFile = confFile.getParentFile();

				if (!parentFile.exists())
					parentFile.mkdirs();

				this.createConfig(file, confFile);
			}
		}

	}

	public void saveConfig()
	{
		for (ShadowsConfFile file : ShadowsConfFile.values())
		{
			if (_configurations.containsKey(file))
				try
				{
					_configurations.get(file).save(new File(file.getPath()));
				}
				catch (IOException e)
				{

				}
		}

		ShadowsAPI.getChatty().logInfo("Configuration saved.");
	}

	public String getProperty(ShadowsConfFile file, String path)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			String prop = conf.getString(path, "NULL");

			if (!prop.equalsIgnoreCase("NULL"))
				return prop;
			conf.set(path, null);
		}

		return null;
	}

	public boolean setProperty(ShadowsConfFile file, String path, String value)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, value);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{

			}
			return true;
		}

		return false;
	}

	public boolean removeProperty(ShadowsConfFile file, String path)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, null);
			return true;
		}

		return false;
	}

	private void createConfig(ShadowsConfFile config, File file)
	{
		switch (config)
		{
		case SETTINGS:
			YamlConfiguration settingsConf = YamlConfiguration
					.loadConfiguration(file);
			settingsConf.set("locale", "en");
			settingsConf.set("vanishLightLevel", new Integer(5));
			settingsConf.set("verboseMode", true);
			settingsConf.set("debugMode", false);
			try
			{
				settingsConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, settingsConf);
			break;

		case LOCALE:
			YamlConfiguration localeConf = YamlConfiguration
					.loadConfiguration(file);
			localeConf.set("en.Vanish", "&7You have become invisible.");
			localeConf.set("en.Reveal", "&7You have been revealed.");
			localeConf.set("en.Appear", "&7You are no longer invisible.");
			localeConf.set("en.Help", "&7%C &7- %D");
			localeConf.set("en.Unable", "&7You are unable to do that.");
			localeConf.set("en.Change", "&F%O &7was changed to &f%N");
			localeConf.set("en.Info", "&7Debug: %D / Verbose: %V / Vanish: %I");
			localeConf.set("en.NewVersionAvailable",
					"&7There is a new version of Shadows available.");
			localeConf.set("en.DebugHasPermission",
					"&F%P &7has permission: &F%D");
			localeConf.set("en.DebugLacksPermission",
					"&F%P &7lacks permission: &F%D");
			try
			{
				localeConf.save(file);
			}
			catch (IOException e1)
			{
			}

			_configurations.put(config, localeConf);
			break;
		}

	}

	public boolean propertyExists(ShadowsConfFile file, String path)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			if (conf.contains(path))
				return true;
		}

		return false;
	}

	public boolean setProperty(ShadowsConfFile file, String path, boolean b)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, b);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{

			}
			return true;
		}

		return false;
	}

	public boolean setProperty(ShadowsConfFile file, String path, Double double1)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, double1);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{

			}
			return true;
		}

		return false;
	}

	public boolean setProperty(ShadowsConfFile file, String path,
			Integer integer)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, integer);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{

			}
			return true;
		}

		return false;
	}
}
