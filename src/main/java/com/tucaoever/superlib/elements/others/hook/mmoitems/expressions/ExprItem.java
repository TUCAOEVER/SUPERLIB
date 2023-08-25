package com.tucaoever.superlib.elements.others.hook.mmoitems.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.api.Description;
import com.tucaoever.superlib.api.Examples;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Examples("mmoitem type \"SWORD\" id \"0010\"")
@Description("get mmoitem with specific type and id")
public class ExprItem extends SimpleExpression<ItemStack> {

    private Expression<String> type;
    private Expression<String> id;

    @Override
    public @NotNull Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, @NotNull Kleenean isDelayed, ParseResult parser) {
        this.type = (Expression<String>) expressions[0];
        this.id = (Expression<String>) expressions[1];
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "MMOItems type " + type + " id " + id;
    }

    @Override
    protected ItemStack @NotNull [] get(@NotNull Event event) {
        String type = this.type.getSingle(event);
        String id = this.id.getSingle(event);
        if (type != null && id != null) {
            ItemStack itemStack = MMOItems.plugin.getItem(type, id);
            if (itemStack == null) return new ItemStack[0];
            return new ItemStack[]{itemStack};
        } else {
            return new ItemStack[0];
        }
    }
}
