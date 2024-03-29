package com.tucaoever.superlib.elements.others.hook.mythicmobs.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.api.Description;
import com.tucaoever.superlib.api.Examples;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Description("get internal name of active mob")
@Examples("set {_mobtype} to mobtype of event-entity")
public class ExprMobType extends SimpleExpression<String> {
    private Expression<Object> entityObject;

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "internal name " + entityObject.getSingle(event);
    }

    @Override
    protected String @NotNull [] get(@NotNull Event event) {
        Object entityObject = this.entityObject.getSingle(event);
        if (entityObject instanceof ActiveMob activeMob) {
            return new String[]{activeMob.getMobType()};
        } else if (entityObject instanceof Entity entity) {
            ActiveMob activeMob = MythicBukkit.inst().getMobManager().getMythicMobInstance(entity);
            if (activeMob == null) return new String[]{};
            return new String[]{activeMob.getMobType()};
        }
        return new String[]{};
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entityObject = (Expression<Object>) exprs[0];
        return entityObject != null;
    }
}
