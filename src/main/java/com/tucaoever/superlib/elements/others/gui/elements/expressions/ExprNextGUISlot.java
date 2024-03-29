package com.tucaoever.superlib.elements.others.gui.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SectionSkriptEvent;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.SUPERLIB;
import com.tucaoever.superlib.elements.others.gui.elements.sections.SecCreateGUI;
import com.tucaoever.superlib.elements.others.gui.elements.sections.SecGUIOpenClose;
import com.tucaoever.superlib.elements.others.gui.elements.sections.SecMakeGUI;
import com.tucaoever.superlib.elements.others.gui.gui.GUI;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Next GUI Slot")
@Description("An expression that returns the number/character of the next open slot in a GUI.")
@Examples("make the next gui slot with dirt named \"Slot: %the next gui slot%\"")
@Since("1.3")
public class ExprNextGUISlot extends SimpleExpression<Character> {

	@Nullable
	private Expression<GUI> guis;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		if (matchedPattern == 2) {
			SkriptEvent skriptEvent = getParser().getCurrentSkriptEvent();
			if (!getParser().isCurrentSection(SecCreateGUI.class) && !(skriptEvent instanceof SectionSkriptEvent
					&& ((SectionSkriptEvent) skriptEvent).isSection(SecCreateGUI.class, SecMakeGUI.class, SecGUIOpenClose.class))) {
				Skript.error("The 'next gui slot' expression must have a GUI specified unless it is used in a GUI section.");
				return false;
			}
			guis = null;
		} else {
			guis = (Expression<GUI>) exprs[0];
		}
		return true;
	}

	@Override
	@Nullable
	protected Character[] get(Event e) {
		if (guis == null) {
			GUI gui = SUPERLIB.getGUIManager().getGUI(e);
			if (gui != null) {
				return new Character[]{gui.nextSlot()};
			}
		}

		GUI[] guis = this.guis.getArray(e);
		int size = guis.length;
		Character[] slots = new Character[size];
		for (int i = 0; i < size; i++) {
			slots[i] = guis[i].nextSlot();
		}
		return slots;
	}

	@Override
	public boolean isSingle() {
		return guis != null && guis.isSingle();
	}

	@Override
	public Class<? extends Character> getReturnType() {
		return Character.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		if (guis != null) {
			return "the next gui slot" + (guis.isSingle() ? "" : "s") + " of " + guis.toString(e, debug);
		} else {
			return "the next gui slot";
		}
	}

}
