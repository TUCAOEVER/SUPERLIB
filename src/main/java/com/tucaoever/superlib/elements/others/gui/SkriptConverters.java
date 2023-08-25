package com.tucaoever.superlib.elements.others.gui;

import ch.njol.skript.registrations.Converters;
import com.tucaoever.superlib.elements.others.gui.gui.GUI;
import org.bukkit.inventory.Inventory;

public class SkriptConverters {

	public SkriptConverters() {
		Converters.registerConverter(GUI.class, Inventory.class, GUI::getInventory);
	}

}
