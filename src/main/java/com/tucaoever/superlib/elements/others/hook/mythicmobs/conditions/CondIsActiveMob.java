package com.tucaoever.superlib.elements.others.hook.mythicmobs.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class CondIsActiveMob extends Condition {

    private Expression<Entity> entity;

    @Override
    public boolean check(Event event) {
        return MythicBukkit.inst().getMobManager().isActiveMob(entity.getSingle(event).getUniqueId());
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.entity = (Expression<Entity>) exprs[0];
        return false;
    }
}
