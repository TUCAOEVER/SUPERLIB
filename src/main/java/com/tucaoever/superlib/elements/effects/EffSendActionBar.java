package com.tucaoever.superlib.elements.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.chat.BungeeConverter;
import ch.njol.skript.util.chat.ChatMessages;
import ch.njol.util.Kleenean;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;

import javax.annotation.Nullable;

public class EffSendActionBar extends Effect {

    @SuppressWarnings("null")
    private Expression<String> message;

    @SuppressWarnings("null")
    private Expression<Player> recipients;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        message = (Expression<String>) exprs[0];
        recipients = (Expression<Player>) exprs[1];
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void execute(final Event e) {
        String msg = message.getSingle(e);
        assert msg != null;
        BaseComponent[] components = BungeeConverter.convert(ChatMessages.parseToArray(msg));
        for (Player player : recipients.getArray(e))
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, components);
    }

    @Override
    public String toString(final @Nullable Event e, final boolean debug) {
        return "send action bar " + message.toString(e, debug) + " to " + recipients.toString(e, debug);
    }

}
