package com.tucaoever.superlib.elements.others.hook.placeholderapi.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.api.Examples;
import com.tucaoever.superlib.api.Patterns;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
@Patterns("(papi|placeholder[[s]api]) parse (string[s]|text) %strings% [(from|of) %offlineplayer%]")
@Examples("set {_s} to papi parse string \"msg %player_name%\" from target player")
public class ExprParse extends SimpleExpression<String> {

    private Expression<String> stringSingle;
    private Expression<OfflinePlayer> offlinePlayerSingle;

    @Override
    protected @Nullable String[] get(Event e) {
        String[] strings = this.stringSingle.getArray(e);
        OfflinePlayer offlinePlayer = this.offlinePlayerSingle.getSingle(e);
        List<String> stringList = new ArrayList<>();
        for (String string : strings) {
            String output = PlaceholderAPI.setPlaceholders(offlinePlayer, string);
            stringList.add(output);
        }
        return stringList.toArray(new String[0]);
    }

    @Override
    public boolean isSingle() {
        return stringSingle.isSingle();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "papi parse string " + stringSingle.toString(e, debug) + " from offline player " + offlinePlayerSingle.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.stringSingle = (Expression<String>) exprs[0];
        this.offlinePlayerSingle = (Expression<OfflinePlayer>) exprs[1];
        return true;
    }
}
