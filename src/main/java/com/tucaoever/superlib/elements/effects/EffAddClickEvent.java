package com.tucaoever.superlib.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.Event;


import javax.annotation.Nullable;

public class EffAddClickEvent extends Effect
{
    private Expression<ClickEvent.Action> action;
    private Expression<String> data;
    private Expression<TextComponent> component;

    public boolean init(final Expression<?>[] e, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        this.action = (Expression<ClickEvent.Action>)e[0];
        this.data = (Expression<String>)e[1];
        this.component = (Expression<TextComponent>)e[2];
        return true;
    }

    public String toString(@Nullable final Event paramEvent, final boolean paramBoolean) {
        return "add click event with action %clickeventaction% (and|with) [(execute|link)] %string% to [text component] %textcomponent%";
    }

    protected void execute(final Event e) {
        if (this.component == null || this.action == null || this.data == null) {
            return;
        }
        (this.component.getSingle(e)).setClickEvent(new ClickEvent(this.action.getSingle(e), this.data.getSingle(e)));
    }
}

