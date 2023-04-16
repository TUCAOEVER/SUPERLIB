package com.tucaoever.superlib.addons.skriptyaml;

import java.util.HashMap;
import java.util.logging.Logger;

import com.tucaoever.superlib.addons.skriptyaml.utils.yaml.YAMLProcessor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SkriptYaml extends JavaPlugin {

	public final static Logger LOGGER = Bukkit.getServer() != null ? Bukkit.getLogger() : Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public final static HashMap<String, YAMLProcessor> YAML_STORE = new HashMap<String, YAMLProcessor>();
	private final static HashMap<String, String> REGISTERED_TAGS = new HashMap<String, String>();

	public static boolean isTagRegistered(String tag) {
		return REGISTERED_TAGS.containsKey(tag);
	}

	public static void debug(String error) {
		LOGGER.warning("[skript-yaml DEBUG] " + error);
	}

	public static void warn(String error) {
		LOGGER.warning("[skript-yaml] " + error);
	}

	public static void error(String error) {
		LOGGER.severe("[skript-yaml] " + error);
	}
}