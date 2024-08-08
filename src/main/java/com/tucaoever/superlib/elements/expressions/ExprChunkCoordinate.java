package com.tucaoever.superlib.elements.expressions;

import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.Chunk;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.util.SimpleExpression;

public class ExprChunkCoordinate extends SimpleExpression<Number>
{
    private Expression<Chunk> chunkExpression;
    private boolean x;
    
    protected Number[] get(final Event event) {
        final Chunk chunk = this.chunkExpression.getSingle(event);
        return new Number[] { this.x ? chunk.getX() : chunk.getZ() };
    }
    
    public boolean isSingle() {
        return true;
    }
    
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
    
    public String toString(final Event event, final boolean b) {
        return "chunk " + (this.x ? "x" : "z") + " of " + this.chunkExpression;
    }
    
    public boolean init(final Expression<?>[] expressions, final int i, final Kleenean kleenean, final SkriptParser.ParseResult parseResult) {
        this.chunkExpression = (Expression<Chunk>)expressions[0];
        this.x = (parseResult.mark == 0);
        return true;
    }
}
