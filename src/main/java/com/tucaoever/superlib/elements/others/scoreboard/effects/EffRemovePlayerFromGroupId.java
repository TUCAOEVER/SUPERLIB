package com.tucaoever.superlib.elements.others.scoreboard.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.DocumentationId;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import com.tucaoever.superlib.SUPERLIB;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Add/Remove Players from Group Scores")
@Description("Add or removed a players group based score.")
@DocumentationId("AddRemovePlayerFromGroupScore")
public class EffRemovePlayerFromGroupId extends Effect {

    // (delete|remove) %player% from group [id based] score %string%

    Expression<Player> player;
    Expression<String> id;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exp, int arg1, Kleenean arg2, ParseResult arg3) {
        player = (Expression<Player>) exp[0];
        id = (Expression<String>) exp[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event evt) {
        SUPERLIB.getBoardManager().removePlayerFromGroupScore(id.getSingle(evt).replace("\"", ""),
                player.getSingle(evt));
    }
}
