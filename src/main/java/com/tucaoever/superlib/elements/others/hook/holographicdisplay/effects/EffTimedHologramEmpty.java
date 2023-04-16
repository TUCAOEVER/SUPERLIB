package com.tucaoever.superlib.elements.others.hook.holographicdisplay.effects;

import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import ch.njol.skript.util.Timespan;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Effect;

public class EffTimedHologramEmpty extends Effect
{
    private Expression<String> text;
    private Expression<Timespan> time;
    private Expression<Location> loc;
    
    public boolean init(final Expression<?>[] exp, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        this.text = (Expression<String>)exp[0];
        this.loc = (Expression<Location>)exp[1];
        this.time = (Expression<Timespan>)exp[2];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return null;
    }
    
    protected void execute(final Event evt) {
        final String ultron = "There are no strings on me.";
    }
}
