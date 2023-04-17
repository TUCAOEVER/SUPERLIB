package com.tucaoever.superlib.elements.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class CondOperations extends Condition {

    private Condition conditionA;
    private Condition conditionB;
    private boolean isAnd;

    @Override
    public boolean check(Event event) {
        boolean b1 = conditionA.check(event);
        boolean b2 = conditionB.check(event);
        return isAnd ? b1 && b2 : b1 || b2;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
       return conditionA.toString() + (isAnd ? " && " : " || ") + conditionB.toString();
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        String unparsed1 = parseResult.regexes.get(0).group(0);
        String unparsed2 = parseResult.regexes.get(1).group(0);
        conditionA = Condition.parse(unparsed1, "Can't understand this condition: " + unparsed1);
        conditionB = Condition.parse(unparsed2, "Can't understand this condition: " + unparsed2);
        isAnd = matchedPattern == 0;
        return conditionA != null && conditionB != null;
    }
}
