package com.tucaoever.superlib.elements.expressions;

import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.skript.lang.Expression;
import java.util.Calendar;
import org.bukkit.event.Event;
import ch.njol.skript.lang.util.SimpleExpression;

public class ExprCalendar extends SimpleExpression<Integer>
{
    private int matchedPattern;
    
    protected Integer[] get(final Event event) {
        final Calendar now = Calendar.getInstance();
        int i = 0;
        if (this.matchedPattern == 0) {
            i = now.get(1);
        }
        if (this.matchedPattern == 1) {
            i = now.get(2) + 1;
        }
        if (this.matchedPattern == 2) {
            i = now.get(5);
        }
        if (this.matchedPattern == 3) {
            i = now.get(11);
        }
        if (this.matchedPattern == 4) {
            i = now.get(12);
        }
        if (this.matchedPattern == 5) {
            i = now.get(13);
        }
        if (this.matchedPattern == 6) {
            i = now.get(14);
        }
        return new Integer[] { i };
    }
    
    public boolean init(final Expression<?>[] expressions, final int i, final Kleenean kleenean, final SkriptParser.ParseResult parseResult) {
        this.matchedPattern = i;
        return true;
    }
    
    public boolean isSingle() {
        return true;
    }
    
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }
    
    public String toString(final Event event, final boolean b) {
        return this.getClass().getName();
    }
}
