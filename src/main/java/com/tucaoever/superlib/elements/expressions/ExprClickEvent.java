package com.tucaoever.superlib.elements.expressions;

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
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Text Component - Click Event")
@Description("Create a new click event. Supports run command, suggest command, open link and copy to clipboard.")
@Examples({"set {_t} to text component from \"Check out my cool website\"",
        "set hover event of {_t} to a new hover event showing \"Clicky clicky to go to spawn!\"",
        "set click event of {_t} to a new click event to open url \"https://my.cool.website\"",
        "send component {_t} to player"})
@Since("1.5.0")
public class ExprClickEvent extends SimpleExpression<ClickEvent> {

    private static final boolean SUPPORTS_CLIPBOARD = Skript.fieldExists(ClickEvent.Action.class, "COPY_TO_CLIPBOARD");

    private int pattern;
    private Expression<Object> object;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.pattern = matchedPattern;
        if (pattern == 3 && !SUPPORTS_CLIPBOARD) {
            Skript.error("'click event to copy %string% to clipboard' is not supported on your server version", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        object = (Expression<Object>) exprs[0];
        return true;
    }

    @Nullable
    @Override
    protected ClickEvent[] get(Event e) {
        if (object == null) return null;

        Object value = object.getSingle(e);
        Action action;

        switch (pattern) {
            case 1:
                action = Action.SUGGEST_COMMAND;
                break;
            case 2:
                action = Action.OPEN_URL;
                break;
            case 3:
                action = Action.COPY_TO_CLIPBOARD;
                break;
            case 4:
                action = Action.CHANGE_PAGE;
                value = "" + (((Number) object.getSingle(e)).intValue());
                break;
            default:
                action = Action.RUN_COMMAND;
        }
        return new ClickEvent[]{new ClickEvent(action, (String) value)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ClickEvent> getReturnType() {
        return ClickEvent.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean d) {
        String[] actions = new String[]{"run command", "suggest command", "open url", "copy to clipboard", "change to page"};
        return "click event to " + actions[pattern] + " " + object.toString(e, d);
    }

}
