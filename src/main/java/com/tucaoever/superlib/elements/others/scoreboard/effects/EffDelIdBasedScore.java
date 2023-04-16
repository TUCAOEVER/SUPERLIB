package com.tucaoever.superlib.elements.others.scoreboard.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import com.tucaoever.superlib.SUPERLIB;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.jetbrains.annotations.Nullable;

@Name("Delete Group Score")
@Description("Deletes a group score.")
public class EffDelIdBasedScore extends Effect {

    // (delete|remove) score [with] id %string%

    private Expression<String> id;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exp, int arg1, Kleenean arg2, ParseResult arg3) {
        id = (Expression<String>) exp[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event evt, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event evt) {
        String usedId = id.getSingle(evt).replace("\"", "");
        if (SUPERLIB.getBoardManager().getScore(usedId) != null) {
            Score score = SUPERLIB.getBoardManager().getScore(usedId);
            Objective obj = score.getObjective();
            obj.getScoreboard().resetScores(score.getEntry());
            SUPERLIB.getBoardManager().deleteScoreId(usedId);
        }
    }
}
