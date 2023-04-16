package com.tucaoever.superlib.elements.expressions.chunk;

import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ch.njol.skript.Skript;
import ch.njol.skript.ScriptLoader;
import com.tucaoever.superlib.elements.events.EvtPlayerChunkChange;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.skript.lang.Expression;
import org.bukkit.Chunk;
import ch.njol.skript.lang.util.SimpleExpression;

public class ExprOldChunk extends SimpleExpression<Chunk>
{
    Chunk chunk;
    
    public Class<? extends Chunk> getReturnType() {
        return Chunk.class;
    }
    
    public boolean isSingle() {
        return true;
    }
    
    public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        if (!ScriptLoader.isCurrentEvent(EvtPlayerChunkChange.class)) {
            Skript.error("You can only use the Old Chunk expression inside Chunk Change event");
            return false;
        }
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "old chunk";
    }
    
    @Nullable
    protected Chunk[] get(final Event e) {
        final EvtPlayerChunkChange event = (EvtPlayerChunkChange)e;
        return new Chunk[] { event.getFrom() };
    }
}
