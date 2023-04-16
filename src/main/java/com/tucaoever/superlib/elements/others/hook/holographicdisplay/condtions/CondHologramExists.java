package com.tucaoever.superlib.elements.others.hook.holographicdisplay.condtions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.elements.others.hook.holographicdisplay.HoloManager;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class CondHologramExists extends Condition
{
    private Expression<String> id;
    
    public boolean init(final Expression<?>[] exp, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        this.id = (Expression<String>)exp[0];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return null;
    }
    
    public boolean check(final Event evt) {
        return this.id != null && HoloManager.isInHoloMap(((String)this.id.getSingle(evt)).replace("\"", ""));
    }
}
