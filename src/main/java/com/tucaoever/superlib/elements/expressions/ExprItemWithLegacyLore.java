package com.tucaoever.superlib.elements.expressions;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class ExprItemWithLegacyLore extends PropertyExpression<ItemType, ItemType> {
    private Expression<String> lore;

    @Override
    protected ItemType[] get(Event event, ItemType[] itemTypes) {
        List<String> lore = Arrays.asList(this.lore.getSingle(event).split("\\|\\|"));
        return get(itemTypes, item -> {
            item = item.clone();
            ItemMeta meta = item.getItemMeta();
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        });
    }

    @Override
    public Class getReturnType() {
        return ItemType.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<ItemType>) exprs[0]);
        lore = (Expression<String>) exprs[1];
        return true;
    }
}