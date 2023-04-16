package com.tucaoever.superlib.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class ExprNewBook extends SimpleExpression<ItemStack>
{
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }
    
    public boolean isSingle() {
        return true;
    }
    
    public boolean init(final Expression<?>[] e, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        return true;
    }
    
    public String toString(@Nullable final Event e, final boolean arg1) {
        return "[a] new [customized] book";
    }
    
    @Nullable
    protected ItemStack[] get(final Event e) {
        return new ItemStack[] { new ItemStack(Material.WRITTEN_BOOK, 1) };
    }
}
