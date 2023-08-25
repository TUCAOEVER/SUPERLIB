package com.tucaoever.superlib.elements.others.gui;

import java.io.IOException;

import ch.njol.skript.util.Version;
import com.tucaoever.superlib.elements.others.gui.gui.GUIManager;
import com.tucaoever.superlib.elements.others.gui.gui.events.GUIEvents;
import com.tucaoever.superlib.elements.others.gui.gui.events.RecipeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

public class SkriptGUI extends JavaPlugin {

	@SuppressWarnings("NotNullFieldNotInitialized")
	private static SkriptGUI instance;
	@SuppressWarnings("NotNullFieldNotInitialized")
	private static GUIManager manager;

	@Override
	public void onEnable() {

		instance = this;
		new SkriptClasses();
		new SkriptConverters();

		// Register manager and events
		manager = new GUIManager();
		getServer().getPluginManager().registerEvents(new GUIEvents(), this);
		if (Skript.classExists("com.destroystokyo.paper.event.player.PlayerRecipeBookClickEvent")) {
			// We need to track this event (see https://github.com/APickledWalrus/skript-gui/issues/33)
			getServer().getPluginManager().registerEvents(new RecipeEvent(), this);
		}

	}

	public static SkriptGUI getInstance() {
		return instance;
	}

	public static GUIManager getGUIManager() {
		return manager;
	}

}
