package com.tucaoever.superlib.elements.others.gui.expressions;

import ch.njol.skript.lang.util.SimpleExpression;
import com.tucaoever.superlib.SUPERLIB;
import com.tucaoever.superlib.elements.others.gui.GUI;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Name("Last GUI/GUI from id")
@Description("It is used to return the last created gui or a gui from a string id.")
@Examples({"open gui last gui for player",
			"open gui (gui with id \"globalGUI\") for player"
})
@Since("1.0.0")
public class ExprLastGUI extends SimpleExpression<GUI> {


	private Expression<String> id;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
		if (matchedPattern == 1)
			id = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	protected GUI[] get(Event e) {
		if (id != null)
			return new GUI[]{SUPERLIB.getGUIManager().getGlobalGUI(id.getSingle(e))};
		return new GUI[]{SUPERLIB.getGUIManager().getGUIEvent(e)};
	}

	@Override
	public Class<?>[] acceptChange(final ChangeMode mode) {
		if (mode == ChangeMode.DELETE && id != null)
			return CollectionUtils.array(Object.class);
		return null;
	}

	@Override
	public void change(final Event e, Object[] delta, ChangeMode mode){
		String id = this.id.getSingle(e);
		if (id != null) {
			GUI gui = SUPERLIB.getGUIManager().removeGlobalGUI(id);
			if (gui != null)
				gui.clear();
		}
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends GUI> getReturnType() {
		return GUI.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return id == null ? "last gui" : "gui with id" + id.toString(e, debug);
	}

}
