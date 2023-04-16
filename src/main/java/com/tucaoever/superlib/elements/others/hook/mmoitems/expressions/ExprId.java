package com.tucaoever.superlib.elements.others.hook.mmoitems.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class ExprId extends SimpleExpression<String> {

    private Expression<ItemStack> item;

    @Override
    protected String[] get(Event event) {
        ItemStack i = item.getSingle(event);
        if (i != null) {
            NBTItem nbtItem = NBTItem.get(i);
            if (nbtItem.hasType()) {
                return new String[]{nbtItem.getString("MMOITEMS_ITEM_ID")};
            }
            return new String[0];
        }
        return new String[0];
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "mmoitems id";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.item = (Expression<ItemStack>) expressions[0];
        return true;
    }
}
