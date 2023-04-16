package com.tucaoever.superlib.elements.others.scoreboard.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.DocumentationId;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import com.tucaoever.superlib.SUPERLIB;

import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Group Score Value")
@Description("These expressions will get either the name or value of a group based score.")
@DocumentationId("GroupScoreValue")
public class ExprScoreNameFromGroupId extends SimpleExpression<String> {

    Expression<String> id;

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
        id = (Expression<String>) exp[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        return null;
    }

    @Override
    @Nullable
    protected String[] get(Event evt) {
        return new String[]{SUPERLIB.getBoardManager().getNameOfGroupScore(id.getSingle(evt).replace("\"", ""))};
    }
}
