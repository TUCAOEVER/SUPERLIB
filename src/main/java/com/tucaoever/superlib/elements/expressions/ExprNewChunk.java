package com.tucaoever.superlib.elements.expressions;

import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ch.njol.skript.Skript;
import ch.njol.skript.ScriptLoader;
import com.tucaoever.superlib.elements.events.EvtChunkChange;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.skript.lang.Expression;
import org.bukkit.Chunk;
import ch.njol.skript.lang.util.SimpleExpression;

public class ExprNewChunk extends SimpleExpression<Chunk>
{
    Chunk chunk;
    
    public Class<? extends Chunk> getReturnType() {
        return Chunk.class;
    }
    
    public boolean isSingle() {
        return true;
    }
    
    public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        if (!ScriptLoader.isCurrentEvent(EvtChunkChange.class)) {
            Skript.error("You can only use the New Chunk expression inside Chunk Change event");
            return false;
        }
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "new chunk";
    }
    
    @Nullable
    protected Chunk[] get(final Event e) {
        final EvtChunkChange event = (EvtChunkChange)e;
        return new Chunk[] { event.getTo() };
    }
}
