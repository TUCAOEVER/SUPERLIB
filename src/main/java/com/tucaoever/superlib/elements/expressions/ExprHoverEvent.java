package com.tucaoever.superlib.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Name("Text Component - Hover Event")
@Description("Create a new hover event. Can show text or an item to a player. 'showing %itemtype%' requires Minecraft 1.16.2+")
@Examples({"set {_t} to text component from \"Check out my cool tool!\"",
        "set hover event of {_t} to a new hover event showing player's tool",
        "send component {_t} to player"})
@Since("1.5.0")
public class ExprHoverEvent extends SimpleExpression<HoverEvent> {
    private static final boolean HAS_TEXT = Skript.classExists("net.md_5.bungee.api.chat.hover.content.Text");

    private Expression<Object> object;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
        object = (Expression<Object>) exprs[0];
        return true;
    }

    @Nullable
    @Override
    protected HoverEvent[] get(Event e) {
        if (object == null) return null;


        String[] string = ((String[]) this.object.getArray(e));
        if (HAS_TEXT) {
            List<Content> texts = new ArrayList<>();
            for (int i = 0; i < string.length; i++) {
                texts.add(new Text(string[i] + (i < (string.length - 1) ? System.lineSeparator() : "")));
            }
            return new HoverEvent[]{new HoverEvent(Action.SHOW_TEXT, texts)};
        } else {
            TextComponent[] comps = new TextComponent[string.length];
            for (int i = 0; i < string.length; i++) {
                comps[i] = new TextComponent(string[i] + (i < (string.length - 1) ? System.lineSeparator() : ""));
            }
            return new HoverEvent[]{new HoverEvent(Action.SHOW_TEXT, comps)};
        }

    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends HoverEvent> getReturnType() {
        return HoverEvent.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean d) {
        return "hover event showing " + object.toString(e, d);
    }

}
