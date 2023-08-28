package com.tucaoever.superlib.elements.others.gui.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SectionSkriptEvent;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.SUPERLIB;
import com.tucaoever.superlib.elements.others.gui.elements.sections.SecGUIOpenClose;
import com.tucaoever.superlib.elements.others.gui.gui.GUI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Name("Cancel GUI Close")
@Description({
		"Cancels or uncancels the closing of a GUI.",
		" This effect can be used within a GUI close section.",
		" A 1 tick delay is applied by this effect after the code has run."
})
@Examples({
		"create a gui with virtual chest inventory with 3 rows named \"My GUI\":",
		"\trun on gui close:",
		"\t\tcancel the gui closing"
})
@Since("1.2.0")
public class EffCancelGUIClose extends Effect {

	private boolean cancel;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		SkriptEvent skriptEvent = getParser().getCurrentSkriptEvent();
		if (!(skriptEvent instanceof SectionSkriptEvent) || !((SectionSkriptEvent) skriptEvent).isSection(SecGUIOpenClose.class)) {
			Skript.error("Cancelling or uncancelling the closing of a GUI can only be done within a GUI close section.");
			return false;
		}
		cancel = parseResult.mark == 0;
		return true;
	}

	@Override
	protected void execute(Event e) {
		GUI gui = SUPERLIB.getGUIManager().getGUI(e);
		if (gui != null) {
			gui.setCloseCancelled(cancel);
		}
	}

	@Override
	public @NotNull String toString(@Nullable Event e, boolean debug) {
		return (cancel ? "cancel" : "uncancel") + " the gui closing";
	}

}
