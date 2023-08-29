package com.tucaoever.superlib.elements.others.hook.mmoitems.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import com.tucaoever.superlib.api.Description;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Description("check whether a item is mmoitem")
public class CondIsItem extends PropertyCondition<ItemStack> {

    @Override
    public boolean check(ItemStack itemStack) {
        return NBTItem.get(itemStack).hasType();
    }

    @Override
    protected @NotNull String getPropertyName() {
        return "mmoitem";
    }
}
