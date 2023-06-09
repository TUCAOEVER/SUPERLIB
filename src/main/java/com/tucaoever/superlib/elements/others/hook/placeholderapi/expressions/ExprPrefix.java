package com.tucaoever.superlib.elements.others.hook.placeholderapi.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.elements.others.hook.placeholderapi.PlaceholderAPIEvent;
import org.bukkit.event.Event;

@Name("PlaceholderAPI Prefix")
@Description("Returns the prefix of the placeholder in a PlaceholderAPI request event.")
@Examples({"on placeholderapi request with prefix \"hello\":",
        "\tif the identifier is \"world\": # Placeholder is hello_world",
        "# The prefix is the part before the first underscore."})
@Since("1.0")
public class ExprPrefix extends SimpleExpression<String> {

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(PlaceholderAPIEvent.class)) {
            Skript.error("The PlaceholderAPI prefix can only be used in a PlaceholderAPI request event", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        return true;
    }

    @Override
    protected String[] get(final Event e) {
        return new String[]{((PlaceholderAPIEvent) e).getPrefix()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the placeholderapi prefix";
    }

}