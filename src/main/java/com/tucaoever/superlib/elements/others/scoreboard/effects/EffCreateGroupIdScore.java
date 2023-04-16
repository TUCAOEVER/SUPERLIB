package com.tucaoever.superlib.elements.others.scoreboard.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Utils;
import ch.njol.util.Kleenean;

import com.tucaoever.superlib.SUPERLIB;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Create Group Based Score")
@Description("Create a group ID based score and assign it to a player or multiple players.")
public class EffCreateGroupIdScore extends Effect {
    private Expression<String> score;
    private Expression<String> id;
    private Expression<Number> value;
    private Expression<Player> players;


    // (set|create) group id [based] score %string% in sidebar for %players% to %number% with id
    // %string%
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exp, int arg1, Kleenean arg2, ParseResult arg3) {
        score = (Expression<String>) exp[0];
        players = (Expression<Player>) exp[1];
        value = (Expression<Number>) exp[2];
        id = (Expression<String>) exp[3];
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event evt) {
        String finalId = id.getSingle(evt).replace("\"", "");
        SUPERLIB.getBoardManager().adjustGroupIdScore(finalId,
                Utils.replaceChatStyles(score.getSingle(evt).replace("\"", "")),
                value.getSingle(evt).intValue());
        for (Player p : players.getArray(evt)) {
            if (p != null && finalId != null) {
                SUPERLIB.getBoardManager().addPlayerToGroupId(finalId, p);
            }
        }
    }
}
