package com.tucaoever.superlib.elements.others.hook.mythicmobs.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.api.Description;
import com.tucaoever.superlib.api.Examples;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Description("check whether world has certain mythic mob")
@Examples("world has active mob \"Skeleton_Knight\"")
public class CondHasActiveMob extends Condition {

    private Expression<World> world;
    private Expression<Object> object;

    @Override
    public boolean check(@NotNull Event e) {
        Object mobObject = object.getSingle(e);
        if (mobObject instanceof ActiveMob activeMob) {
            return MythicBukkit.inst().getMobManager()
                    .getActiveMobs()
                    .stream()
                    .filter(mob -> BukkitAdapter.adapt(mob.getLocation().getWorld()) == world)
                    .anyMatch(mob -> mob == activeMob);
        } else if (mobObject instanceof String mobType) {
            return MythicBukkit.inst().getMobManager()
                    .getActiveMobs()
                    .stream()
                    .filter(activeMob -> BukkitAdapter.adapt(activeMob.getLocation().getWorld()) == world)
                    .anyMatch(activeMob -> activeMob.getMobType().equalsIgnoreCase(mobType));
        }
        return false;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return PropertyCondition.toString(this, PropertyCondition.PropertyType.HAVE, e, debug, world,
                "active mob " + object.toString(e, debug));
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.world = (Expression<World>) exprs[0];
        this.object = (Expression<Object>) exprs[1];
        setNegated(matchedPattern == 1);
        return true;
    }
}
