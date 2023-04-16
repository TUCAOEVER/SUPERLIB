package com.tucaoever.superlib.elements.others.hook.mmoitems.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class ExprItem extends SimpleExpression<ItemStack> {

    private Expression<String> type;
    private Expression<String> item;

    @Override
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        this.type = (Expression<String>) expressions[0];
        this.item = (Expression<String>) expressions[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "mmoitems";
    }

    @Override
    @Nullable
    protected ItemStack[] get(Event event) {
        String stype = type.getSingle(event);
        String sitem = item.getSingle(event);
        if (stype != null && sitem != null) {
            Type t = MMOItems.plugin.getTypes().get(stype);
            if (t == null) {
                return new ItemStack[0];
            }
            ItemStack i = MMOItems.plugin.getItem(t, sitem);
            if (i == null) {
                return new ItemStack[0];
            }
            return new ItemStack[]{i};
        }
        return new ItemStack[0];
    }
}
