package com.tucaoever.superlib.elements.others.gui.elements.conditions;

import com.tucaoever.superlib.SUPERLIB;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.jetbrains.annotations.NotNull;

@Name("Has GUI")
@Description("Checks whether the given player(s) has/have a GUI open.")
@Examples({
		"command /guiviewers: # Returns a list of all players with a GUI open.",
		"\tset {_viewers::*} to all players where [input has a gui]",
		"\tsend \"GUI Viewers: %{_viewers::*}%\" to player"
})
@Since("1.0.0")
public class CondHasGUI extends Condition {

	@SuppressWarnings("NotNullFieldNotInitialized")
	private Expression<Player> players;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean kleenean, ParseResult parseResult) {
		players = (Expression<Player>) exprs[0];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public boolean check(Event e) {
		return players.check(e, p -> SUPERLIB.getGUIManager().hasGUI(p), isNegated());
	}

	@Override
	public @NotNull String toString(Event e, boolean debug) {
		return players.toString(e, debug) + (!isNegated() ? " has/have " : " do not/don't have ") + " a gui open";
	}

}
