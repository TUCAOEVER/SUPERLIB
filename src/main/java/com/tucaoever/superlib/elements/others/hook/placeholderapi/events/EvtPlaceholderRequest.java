package com.tucaoever.superlib.elements.others.hook.placeholderapi.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;

import com.tucaoever.superlib.SUPERLIB;
import com.tucaoever.superlib.elements.others.hook.placeholderapi.PlaceholderAPIEvent;
import com.tucaoever.superlib.elements.others.hook.placeholderapi.PlaceholderAPIListener;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.Event;

@Name("PlaceholderAPI Placeholder Request Event")
@Description("Called whenever a placeholder is requested by PlaceholderAPI.")
@Examples({"on placeholderapi request with prefix \"double\":",
        "\tif the identifier is \"health\": # The placeholder is double_health",
        "\t\tset the result to player's health * 2 "})
@Since("1.0")
public class EvtPlaceholderRequest extends SkriptEvent {

    private String prefix;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(final Literal<?>[] args, final int matchedPattern, final SkriptParser.ParseResult parser) {
        Literal<String> l = (Literal<String>) args[0];
        if (l == null)
            return false;
        prefix = l.getSingle();
        if (StringUtils.isBlank(prefix)) {
            Skript.error(prefix + " is not a valid placeholder", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        new PlaceholderAPIListener(SUPERLIB.getInstance(), prefix).register();
        return true;
    }

    @Override
    public boolean check(final Event e) {
        return ((PlaceholderAPIEvent) e).getPrefix().equalsIgnoreCase(prefix);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "placeholderapi placeholder request" + (prefix != null ? ("for prefix \"" + prefix + "\"") : "");
    }

}