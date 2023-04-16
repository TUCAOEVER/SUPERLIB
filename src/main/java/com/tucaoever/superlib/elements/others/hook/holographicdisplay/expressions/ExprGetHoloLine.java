package com.tucaoever.superlib.elements.others.hook.holographicdisplay.expressions;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import javax.annotation.Nullable;

import com.tucaoever.superlib.elements.others.hook.holographicdisplay.HoloManager;
import org.bukkit.event.Event;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.util.SimpleExpression;

public class ExprGetHoloLine extends SimpleExpression<String>
{
    private Expression<String> id;
    private Expression<Number> line;
    
    public boolean isSingle() {
        return true;
    }
    
    public boolean init(final Expression<?>[] exp, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        this.id = (Expression<String>)exp[1];
        this.line = (Expression<Number>)exp[0];
        return true;
    }
    
    public String toString(@Nullable final Event evt, final boolean arg1) {
        return null;
    }
    
    @Nullable
    protected String[] get(final Event evt) {
        if (HoloManager.getFromHoloMap(((String)this.id.getSingle(evt)).replace("\"", "")) == null) {
            return new String[1];
        }
        final Hologram hologram = HoloManager.getFromHoloMap(((String)this.id.getSingle(evt)).replace("\"", ""));
        String finalLine = hologram.getLine(((Number)this.line.getSingle(evt)).intValue()).toString();
        if (finalLine.indexOf("text=") != -1) {
            finalLine = finalLine.substring(finalLine.indexOf("text=") + 5, finalLine.indexOf("]"));
            return new String[] { finalLine };
        }
        if (finalLine.indexOf("itemStack=") != -1) {
            finalLine = finalLine.substring(finalLine.indexOf("itemStack=") + 10, finalLine.indexOf(","));
            return new String[] { finalLine };
        }
        return new String[1];
    }
    
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
