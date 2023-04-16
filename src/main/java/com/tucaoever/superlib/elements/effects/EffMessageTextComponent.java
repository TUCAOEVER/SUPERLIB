package com.tucaoever.superlib.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffMessageTextComponent extends Effect
{
    private Expression<TextComponent> component;
    private Expression<Player> players;

    public boolean init(final Expression<?>[] e, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        this.component = (Expression<TextComponent>)e[0];
        this.players = (Expression<Player>)e[1];
        return true;
    }

    public String toString(@Nullable final Event paramEvent, final boolean paramBoolean) {
        return "message text component %textcomponent% [to %players%]";
    }

    protected void execute(final Event e) {
        if (this.component == null || this.players == null) {
            return;
        }
        Player[] array;
        for (int length = (array = this.players.getAll(e)).length, i = 0; i < length; ++i) {
            final Player p = array[i];
            p.spigot().sendMessage(this.component.getSingle(e));
        }
    }
}
