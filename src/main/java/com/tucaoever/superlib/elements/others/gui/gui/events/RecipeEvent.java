package com.tucaoever.superlib.elements.others.gui.gui.events;

import com.destroystokyo.paper.event.player.PlayerRecipeBookClickEvent;
import com.tucaoever.superlib.SUPERLIB;
import com.tucaoever.superlib.elements.others.gui.gui.GUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class RecipeEvent implements Listener {

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onRecipeBookClick(PlayerRecipeBookClickEvent event) {
		GUI gui = SUPERLIB.getGUIManager().getGUI(event.getPlayer().getOpenInventory().getTopInventory());
		if (gui != null) event.setCancelled(true);
	}

}
