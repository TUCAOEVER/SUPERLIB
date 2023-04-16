package com.tucaoever.superlib.elements.others.hook.mmoitems.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CondIsItem extends Condition {

    private Expression<ItemStack> items;

    @Override
    public boolean check(Event event) {
        return items.check(event, item -> NBTItem.get(item).hasType());
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.items = (Expression<ItemStack>)expressions[0];
        return true;
    }
}
