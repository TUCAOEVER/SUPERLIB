package com.tucaoever.superlib.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffAddHoverEvent extends Effect
{
    private Expression<HoverEvent.Action> action;
    private Expression<String> data;
    private Expression<TextComponent> textcomponent;

    public boolean init(final Expression<?>[] e, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        this.action = (Expression<HoverEvent.Action>)e[0];
        this.data = (Expression<String>)e[1];
        this.textcomponent = (Expression<TextComponent>)e[2];
        return true;
    }

    public String toString(@Nullable final Event paramEvent, final boolean paramBoolean) {
        return "add click event with action %clickeventaction% with [(value|text|link)] %string% to [text component] %textcomponent%";
    }

    protected void execute(final Event e) {
        if (this.textcomponent == null || this.action == null || this.data == null) {
            return;
        }
        (this.textcomponent.getSingle(e)).setHoverEvent(new HoverEvent(this.action.getSingle(e), new ComponentBuilder(this.data.getSingle(e)).create()));
    }
}
