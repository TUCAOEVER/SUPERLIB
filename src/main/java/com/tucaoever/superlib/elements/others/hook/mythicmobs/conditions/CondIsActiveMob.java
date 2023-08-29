package com.tucaoever.superlib.elements.others.hook.mythicmobs.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.api.Examples;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
@Examples("target entity is activemob")
public class CondIsActiveMob extends PropertyCondition<Entity> {

    @Override
    public boolean check(Entity entity) {
        return MythicBukkit.inst().getMobManager().isActiveMob(entity.getUniqueId());
    }

    @Override
    protected @NotNull String getPropertyName() {
        return "active mob";
    }
}
