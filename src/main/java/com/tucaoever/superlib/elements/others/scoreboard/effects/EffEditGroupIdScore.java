package com.tucaoever.superlib.elements.others.scoreboard.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Utils;
import ch.njol.util.Kleenean;

import com.tucaoever.superlib.SUPERLIB;

import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

// (edit|update) score [with] (group|id) %string% to %string% and %number%

@Name("Update Group Score")
@Description("Updates a group based score.")
public class EffEditGroupIdScore extends Effect {

    Expression<String> id;
    private Expression<String> newName;
    private Expression<Number> newScore;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exp, int arg1, Kleenean arg2, ParseResult arg3) {
        id = (Expression<String>) exp[0];
        newName = (Expression<String>) exp[1];
        newScore = (Expression<Number>) exp[2];

        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event evt) {
        if (id != null && newName != null && newScore != null) {
            SUPERLIB.getBoardManager().groupUpdateScore(id.getSingle(evt).replace("\"", ""),
                    Utils.replaceChatStyles(newName.getSingle(evt).replace("\"", "")),
                    newScore.getSingle(evt).intValue());
        }
    }
}
