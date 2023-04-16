package com.tucaoever.superlib.elements.others.gui.sections;

import com.tucaoever.superlib.SUPERLIB;
import com.tucaoever.superlib.elements.others.gui.GUI;
import com.tucaoever.superlib.util.EffectSection;
import com.tucaoever.superlib.util.VariableUtils;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Close GUI")
@Description("A section that will run when the user closes the GUI. This section is optional.")
@Examples({"create a gui with virtual chest inventory with 3 rows named \"My GUI\"",
			"\trun on gui close:",
			"\t\tsend \"You just closed this GUI!\" to player"
})
@Since("1.0.0")
public class SecOnCloseGUI extends EffectSection {

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
		if (checkIfCondition())
			return false;

		if (!isCurrentSection(SecCreateGUI.class)) {
			Skript.error("GUI close sections can only be put within GUI creation or editing sections.");
			return false;
		}

		if (!hasSection()) {
			Skript.error("A GUI close section is pointless without any content! Make sure you put some.");
			return false;
		}

		loadSection("gui close", false, InventoryCloseEvent.class);
		return true;
	}

	@Override
	public void execute(Event e) {
		if (hasSection()) {
			GUI gui = SUPERLIB.getGUIManager().getGUIEvent(e);
			if (gui != null) {
				Object vars = VariableUtils.getInstance().copyVariables(e);
				gui.setOnClose(event -> {
					VariableUtils.getInstance().pasteVariables(event, vars);
					runSection(event);
				});
			}
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "run on gui close";
	}

}
