package com.tucaoever.superlib.elements.others.scoreboard.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.jetbrains.annotations.Nullable;

@Name("Name/Title of Sidebar")
@Description("Get the name/title of a players side bar.")
public class ExprScoreBoardTitle extends SimpleExpression<String> {

    // sidebar (title|name) for %player%

    private Expression<Player> player;

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exp, int arg1, Kleenean arg2, ParseResult arg3) {
        player = (Expression<Player>) exp[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected String[] get(Event evt) {
        if (player != null
                && player.getSingle(evt).getScoreboard().getObjective("sidebarHold") != null) {
            Objective objective = player.getSingle(evt).getScoreboard().getObjective(DisplaySlot.SIDEBAR);
            return new String[]{objective.getDisplayName()};
        }
        return null;
    }
}
