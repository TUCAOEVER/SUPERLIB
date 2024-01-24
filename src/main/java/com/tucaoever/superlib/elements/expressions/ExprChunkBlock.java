package com.tucaoever.superlib.elements.expressions;

import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.Chunk;
import ch.njol.skript.lang.Expression;
import org.bukkit.block.Block;
import ch.njol.skript.lang.util.SimpleExpression;

public class ExprChunkBlock extends SimpleExpression<Block>
{
    private Expression<Chunk> chunkExpression;
    private Expression<Number> xExpr;
    private Expression<Number> yExpr;
    private Expression<Number> zExpr;
    private int level;
    private boolean south;
    private boolean east;
    private boolean center;
    public static int NS;
    public static int EW;
    public static int CORCEN;
    
    static {
        ExprChunkBlock.NS = 4;
        ExprChunkBlock.EW = 8;
        ExprChunkBlock.CORCEN = 16;
    }
    
    public ExprChunkBlock() {
        this.xExpr = null;
    }
    
    public static int getLevel(final int level, final Expression<Number> expression, final Event event) {
        switch (level) {
            case 0: {
                return ((Number)expression.getSingle(event)).intValue();
            }
            case 1: {
                return 255;
            }
            case 2: {
                return 0;
            }
            case 3: {
                return 63;
            }
            default: {
                throw new IllegalArgumentException("level = " + level + " is not 0, 1, 2, 3");
            }
        }
    }
    
    public static String levelString(final int level, final Expression<Number> expression) {
        switch (level) {
            case 0: {
                return "layer " + expression;
            }
            case 1: {
                return "top";
            }
            case 2: {
                return "bottom";
            }
            case 3: {
                return "sea level";
            }
            default: {
                throw new IllegalArgumentException("level = " + level + " is not 0, 1, 2, 3");
            }
        }
    }
    
    protected Block[] get(final Event event) {
        int x;
        int y;
        int z;
        if (this.xExpr != null) {
            x = this.xExpr.getSingle(event).intValue();
            y = this.yExpr.getSingle(event).intValue();
            z = this.zExpr.getSingle(event).intValue();
        }
        else {
            y = getLevel(this.level, this.yExpr, event);
            if (this.center) {
                x = (this.east ? 8 : 7);
                z = (this.south ? 8 : 7);
            }
            else {
                x = (this.east ? 15 : 0);
                z = (this.south ? 15 : 0);
            }
        }
        return new Block[] { this.chunkExpression.getSingle(event).getBlock(x, y, z) };
    }
    
    public boolean isSingle() {
        return true;
    }
    
    public Class<? extends Block> getReturnType() {
        return Block.class;
    }
    
    public String toString(final Event event, final boolean b) {
        if (this.xExpr != null) {
            return "block at " + this.xExpr + ", " + this.yExpr + ", " + this.zExpr + " in " + this.chunkExpression;
        }
        return String.valueOf(levelString(this.level, this.yExpr)) + " " + (this.south ? "south" : "north") + (this.east ? "east " : "west") + " " + (this.center ? "center" : "corner") + " of " + this.chunkExpression;
    }
    
    public boolean init(final Expression<?>[] expressions, final int i, final Kleenean kleenean, final SkriptParser.ParseResult parseResult) {
        if (i == 0) {
            this.xExpr = (Expression<Number>)expressions[0];
            this.yExpr = (Expression<Number>)expressions[1];
            this.zExpr = (Expression<Number>)expressions[2];
            this.chunkExpression = (Expression<Chunk>)expressions[3];
        }
        else {
            final int mark = parseResult.mark;
            this.yExpr = (Expression<Number>)expressions[0];
            this.level = mark % 4;
            this.south = ((mark & ExprChunkBlock.NS) == 0x0);
            this.east = ((mark & ExprChunkBlock.EW) == 0x0);
            this.center = ((mark & ExprChunkBlock.CORCEN) == 0x0);
            this.chunkExpression = (Expression<Chunk>)expressions[1];
        }
        return true;
    }
}
