package com.tucaoever.superlib.elements.others.scoreboard.conditions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.DisplaySlot;
import org.jetbrains.annotations.Nullable;

@Name("Sidebar Is Set")
@Description({"Check if sidebar:",
        "* Is set",
        "* For player",
        "This will check if a sidebar is being displayed to a player"})
public class CondIsScoreboardSet extends Condition {

    private Expression<Player> player;

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
    public boolean check(Event evt) {
        return player.getSingle(evt).getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null;
    }
}
