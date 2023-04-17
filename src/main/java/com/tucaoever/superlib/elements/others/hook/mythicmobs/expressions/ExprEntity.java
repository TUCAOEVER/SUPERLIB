package com.tucaoever.superlib.elements.others.hook.mythicmobs.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.api.Description;
import com.tucaoever.superlib.api.Patterns;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Description("get entity from active mob")
@Patterns("entity (of|from) active[ ]mob %activemob%")
public class ExprEntity extends SimpleExpression<Entity> {

    private Expression<ActiveMob> activeMobSingle;

    @Override
    protected @Nullable Entity[] get(Event e) {
        ActiveMob activeMob = activeMobSingle.getSingle(e);
        if (activeMob == null) return null;
        return new Entity[]{activeMob.getEntity().getBukkitEntity()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Entity> getReturnType() {
        return Entity.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.activeMobSingle = (Expression<ActiveMob>) exprs[0];
        return activeMobSingle != null;
    }
}
