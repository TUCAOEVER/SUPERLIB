package com.tucaoever.superlib.elements.others.file.expressions;

import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import com.google.common.io.Files;
import com.tucaoever.superlib.SUPERLIB;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.util.SimpleExpression;

public class ExprFileName extends SimpleExpression<String>
{
    private Expression<String> path;
    private int ty;
    
    @Nullable
    protected String[] get(final Event e) {
        final String pth = SUPERLIB.getDefaultPath((String)this.path.getSingle(e));
        try {
            if (this.ty == 0) {
                return new String[] { Files.getNameWithoutExtension(pth) };
            }
            return new String[] { Files.getFileExtension(pth) };
        }
        catch (Exception x) {
            SUPERLIB.log("File: '" + pth + "' doesn't exist!");
            return null;
        }
    }
    
    public boolean init(final Expression<?>[] e, final int i, final Kleenean k, final SkriptParser.ParseResult p) {
        this.path = (Expression<String>)e[0];
        this.ty = p.mark;
        return true;
    }
    
    public Class<? extends String> getReturnType() {
        return String.class;
    }
    
    public boolean isSingle() {
        return true;
    }
    
    public String toString(@Nullable final Event e, final boolean b) {
        return this.getClass().getName();
    }
}
