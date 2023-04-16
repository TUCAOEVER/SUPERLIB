package com.tucaoever.superlib.elements.others.hook.placeholderapi.expressions;

import ch.njol.skript.lang.util.*;
import ch.njol.skript.doc.*;
import org.bukkit.entity.*;
import me.clip.placeholderapi.*;
import ch.njol.util.*;
import org.bukkit.event.*;
import java.util.*;
import ch.njol.skript.lang.*;

@Name("Value of Placeholder")
@Description("Returns the value of a PlaceholderAPI/MVdWPlaceholderAPI placeholder.")
@Examples({"command /ping <player>:",
        "\ttrigger:",
        "\t\tset {_ping} to placeholder \"player_ping\" from arg-1 # PlaceholderAPI",
        "\t\tsend \"Ping of %arg-1%: %{_ping}%\" to player"})
@Since("1.3 - Updated Syntax")

public class ExprPlaceholder extends SimpleExpression<String> {

    private Expression<String> placeholders;
    private Expression<Player> players;

    private String formatPlaceholder(String placeholder) {
        if (placeholder == null) {
            return null;
        }
        if (placeholder.charAt(0) == '%') {
            placeholder = placeholder.substring(1);
        }
        if (placeholder.charAt(placeholder.length() - 1) == '%') {
            placeholder = placeholder.substring(0, placeholder.length() - 1);
        }
        return "%" + placeholder + "%";
    }

    private String getPlaceholder(String placeholder, Player player) {
        String value;
        placeholder = formatPlaceholder(placeholder);
        if (PlaceholderAPI.containsPlaceholders(placeholder)) {
            value = PlaceholderAPI.setPlaceholders(player, placeholder);
            if (value.equals(placeholder))
                return null;
            return value;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        placeholders = (Expression<String>) exprs[0];
        players = (Expression<Player>) exprs[1];
        return true;
    }

    @Override
    protected String[] get(final Event e) {
        String[] placeholders = this.placeholders.getArray(e);
        Player[] players = this.players.getArray(e);
        List<String> values = new ArrayList<>();
        if (players.length != 0) {
            for (String pl : placeholders) {
                for (Player p : players) {
                    values.add(getPlaceholder(pl, p));
                }
            }
        } else {
            for (String pl : placeholders) {
                values.add(getPlaceholder(pl, null));
            }
        }
        return values.toArray(new String[0]);
    }

    @Override
    public boolean isSingle() {
        return placeholders.isSingle() && players.isSingle();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the value of placeholder " + placeholders.toString(e, debug) + " from " + players.toString(e, debug);
    }

}
