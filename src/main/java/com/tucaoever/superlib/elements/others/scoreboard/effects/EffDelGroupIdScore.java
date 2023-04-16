package com.tucaoever.superlib.elements.others.scoreboard.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import static com.tucaoever.superlib.SUPERLIB.scoreboardManager;

@Name("Delete Group Score")
@Description("Deletes a group score.")
public class EffDelGroupIdScore extends Effect {

    Expression<String> id;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exp, int arg1, Kleenean arg2, ParseResult arg3) {
        id = (Expression<String>) exp[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event evt) {
        scoreboardManager.deleteGroupScore(id.getSingle(evt).replace("\"", ""));
    }
}
