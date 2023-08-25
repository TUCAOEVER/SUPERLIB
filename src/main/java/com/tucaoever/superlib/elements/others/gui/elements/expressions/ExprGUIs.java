package com.tucaoever.superlib.elements.others.gui.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.elements.others.gui.SkriptGUI;
import com.tucaoever.superlib.elements.others.gui.gui.GUI;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprGUIs extends SimpleExpression<GUI> {

	static {
		Skript.registerExpression(ExprGUIs.class, GUI.class, ExpressionType.SIMPLE, "all guis");
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		return true;
	}

	@Override
	@Nullable
	protected GUI[] get(Event e) {
		return SkriptGUI.getGUIManager().getTrackedGUIs().toArray(new GUI[0]);
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends GUI> getReturnType() {
		return GUI.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "all guis";
	}

}
