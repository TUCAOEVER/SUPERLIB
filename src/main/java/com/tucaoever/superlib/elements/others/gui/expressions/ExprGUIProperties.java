package com.tucaoever.superlib.elements.others.gui.expressions;

import com.tucaoever.superlib.SUPERLIB;
import com.tucaoever.superlib.elements.others.gui.GUI;
import com.tucaoever.superlib.elements.others.gui.GUI.ShapeMode;
import com.tucaoever.superlib.elements.others.gui.sections.SecCreateGUI;
import com.tucaoever.superlib.util.EffectSection;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

import javax.annotation.Nullable;


@Name("GUI Properties")
@Description("Different properties of the GUI. They can be modified.")
@Examples({"edit gui last gui:",
			"\tset the gui-inventory-name to \"New GUI Name!\"",
			"\tset the gui-size to 3 # Sets the number of rows to 3 (if possible)",
			"\tset the gui-shape to \"xxxxxxxxx\", \"x-------x\", and \"xxxxxxxxx\"",
			"\tset the gui-lock-status to false # Players can take items from this GUI now"
})
@Since("1.0.0")
public class ExprGUIProperties extends SimpleExpression<Object> {

	private int pattern;
	private ShapeMode shapeMode;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
		if (!EffectSection.isCurrentSection(SecCreateGUI.class)) {
			Skript.error("You can't change or get the GUI properties outside of a GUI creation or editing section.");
			return false;
		}

		pattern = matchedPattern;

		if (parseResult.mark == 1) {
			shapeMode = ShapeMode.ITEMS;
		} else if (parseResult.mark == 2) {
			shapeMode = ShapeMode.ACTIONS;
		} else {
			shapeMode = ShapeMode.BOTH;
		}

		return true;
	}

	@Override
	@Nullable
	protected Object[] get(Event e) {
		GUI gui = SUPERLIB.getGUIManager().getGUIEvent(e);
		if (gui != null) {
			switch (pattern) {
				case 0: return new String[]{gui.getName()};
				case 1: return new Number[]{gui.getInventory().getSize()};
				case 2: return new String[]{gui.getRawShape()};
				case 3: return new Boolean[]{!gui.isStealable()};
			}
		}
		return new Object[]{};
	}

	@Override
	public Class<?>[] acceptChange(final ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET) {
			switch (pattern) {
				case 0: return CollectionUtils.array(String.class);
				case 1: return CollectionUtils.array(Number.class);
				case 2: return CollectionUtils.array(String[].class);
				case 3: return CollectionUtils.array(Boolean.class);
			}
		}
		return null;
	}

	@Override
	public void change(final Event e, Object[] delta, ChangeMode mode) {
		if (delta == null || (mode != ChangeMode.SET && mode != ChangeMode.RESET))
			return;
		GUI gui = SUPERLIB.getGUIManager().getGUIEvent(e);
		if (gui != null) {
			switch (mode) {
				case SET:
					switch (pattern) {
						case 0: gui.setName((String) delta[0]); break;
						case 1: gui.setSize(((Number) delta[0]).intValue()); break;
						case 2: gui.setShape(false, shapeMode, (String[]) delta); break;
						case 3: gui.setStealable(!(Boolean) delta[0]); break;
					}
					break;
				case RESET:
					switch (pattern) {
						case 0: gui.setName(gui.getInventory().getType().getDefaultTitle()); break;
						case 1: gui.setSize(gui.getInventory().getType().getDefaultSize()); break;
						case 2: gui.setShape(true, null); break; // Reset shape to default
						case 3: gui.setStealable(false); break;
					}
					break;
				default:
					assert false;
			}
		}
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends Object> getReturnType() {
		switch (pattern) {
			case 0: return String.class;
			case 1: return Number.class;
			case 2: return String.class;
			case 3: return Boolean.class;
			default: return Object.class;
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		switch (pattern) {
			case 0: return "the gui inventory name";
			case 1: return "the total number of gui rows";
			case 2: return "the gui shape of " + shapeMode.name().toLowerCase();
			case 3: return "the gui lock status";
			default: return "gui properties";
		}
	}

}
