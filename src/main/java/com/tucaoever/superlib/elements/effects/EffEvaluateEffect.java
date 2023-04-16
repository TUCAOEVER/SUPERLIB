package com.tucaoever.superlib.elements.effects;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

public class EffEvaluateEffect extends Effect {

    private Expression<?> effect;

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        effect = expressions[0];
        return true;
    }

    @Override
    protected void execute(Event event) {
        String pre = null;
        Object object = effect.getSingle(event);
        if (object instanceof String) pre = (String) object;
        if (pre == null) return;
        ScriptLoader.setCurrentEvent("this", event.getClass());
        Effect e = Effect.parse(pre, null);
        ScriptLoader.deleteCurrentEvent();
        if (e == null) return;
        e.run(event);
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "evaluate " + effect.toString(event, debug);
    }

}