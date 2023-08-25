package com.tucaoever.superlib.elements.others.hook.mmoitems.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.api.Description;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Description("get item's mmoitem id (if exists)")
public class ExprId extends SimpleExpression<String> {

    private Expression<ItemStack> item;

    @Override
    protected String @NotNull [] get(@NotNull Event event) {
        ItemStack itemStack = item.getSingle(event);
        if (itemStack == null) return new String[0];
        NBTItem nbtItem = NBTItem.get(itemStack);
        return new String[]{nbtItem.getString("MMOITEMS_ITEM_ID")};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "get item " + item.getSingle(event) + " mmoitem id";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.item = (Expression<ItemStack>) expressions[0];
        return true;
    }
}
