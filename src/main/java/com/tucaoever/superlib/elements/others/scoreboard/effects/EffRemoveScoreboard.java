package com.tucaoever.superlib.elements.others.scoreboard.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import com.tucaoever.superlib.SUPERLIB;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.DisplaySlot;
import org.jetbrains.annotations.Nullable;

@Name("Wipe/Delete/Remove Sidebar")
@Description({"Delete a sidebar by:",
        "* Scoreboard",
        "* Player",
        "This will completely wipe a specified player's sidebar deleting all scores " +
        "and the name of the sidebar."})
public class EffRemoveScoreboard extends Effect {

    // (wipe|erase) %player%['s] sidebar

    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exp, int arg1, Kleenean arg2, ParseResult arg3) {
        player = (Expression<Player>) exp[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event evt, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event evt) {
        if (player.getSingle(evt).getScoreboard().getObjective("sidebarHold") != null) {
            SUPERLIB.getBoardManager().removeSingleScoresFromPlayer(player.getSingle(evt));
            SUPERLIB.getBoardManager().removeGroupScoresFromPlayer(player.getSingle(evt));
            player.getSingle(evt).getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
        }
    }
}
