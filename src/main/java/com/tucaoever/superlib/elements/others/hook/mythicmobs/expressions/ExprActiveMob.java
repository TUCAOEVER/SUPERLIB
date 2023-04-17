package com.tucaoever.superlib.elements.others.hook.mythicmobs.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.api.Description;
import com.tucaoever.superlib.api.Patterns;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Description("get active mob from entity")
@Patterns("active[ ]mob (of|from) %entity%")
public class ExprActiveMob extends SimpleExpression<ActiveMob> {

    private Expression<Entity> entitySingle;

    @Override
    protected @Nullable ActiveMob[] get(Event e) {
        ActiveMob activeMob = MythicBukkit.inst().getMobManager().getMythicMobInstance(entitySingle.getSingle(e));
        if (activeMob == null) return null;
        return new ActiveMob[]{activeMob};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ActiveMob> getReturnType() {
        return ActiveMob.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.entitySingle = (Expression<Entity>) exprs[0];
        return entitySingle != null;
    }
}
