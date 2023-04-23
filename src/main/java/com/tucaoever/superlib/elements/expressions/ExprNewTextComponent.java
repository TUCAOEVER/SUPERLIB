package com.tucaoever.superlib.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprNewTextComponent extends SimpleExpression<TextComponent>
{
    private Expression<String> string;

    public Class<? extends TextComponent> getReturnType() {
        return TextComponent.class;
    }

    public boolean isSingle() {
        return true;
    }

    public boolean init(final Expression<?>[] e, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        this.string = (Expression<String>)e[0];
        return true;
    }

    public String toString(@Nullable final Event e, final boolean arg1) {
        return "[a] [new] text component [with [text]] %string%";
    }

    @Nullable
    protected TextComponent[] get(final Event e) {
        return (this.string != null) ? new TextComponent[] { new TextComponent(this.string.getSingle(e)) } : null;
    }
}
