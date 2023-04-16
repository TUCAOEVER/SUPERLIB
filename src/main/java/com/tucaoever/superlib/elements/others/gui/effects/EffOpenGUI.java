package com.tucaoever.superlib.elements.others.gui.effects;

import com.tucaoever.superlib.elements.others.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import javax.annotation.Nullable;

@Name("Open GUI")
@Description("Opens the given GUI for the given players.")
@Examples({"create a gui with virtual chest inventory named \"My GUI Name\" with 3 rows",
			"open gui last gui for player"
})
@Since("1.0.0")
public class EffOpenGUI extends Effect {

	private Expression<GUI> gui;
	private Expression<Player> players;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		gui = (Expression<GUI>) exprs[0];
		players = (Expression<Player>) exprs[1];
		return true;
	}

	@Override
	protected void execute(Event e) {
		GUI gui = this.gui.getSingle(e);
		if (gui != null) {
			for (Player p : players.getArray(e))
				p.openInventory(gui.getInventory());
		}
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "open gui " + gui.toString(e, debug) + " to " + players.toString(e, debug);
	}

}