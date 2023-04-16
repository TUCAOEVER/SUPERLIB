package com.tucaoever.superlib.elements.others.hook.mythicmobs.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.api.Description;
import com.tucaoever.superlib.api.Examples;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Description("a way to spawn specific id mythic mob at certain location.")
@Examples("spawn \"Skeleton_Knight\" at location of player")
public class EffSpawnMythicMob extends Effect {

    private Expression<String> mobIdSingle;
    private Expression<Location> mobLocSingle;
    private Expression<Integer> mobNumSingle;
    private int matchedPattern;

    @Override
    protected void execute(@NotNull Event event) {

        String mobId = mobIdSingle.getSingle(event);
        Location location = mobLocSingle.getSingle(event);
        if (location == null) return;
        World world = location.getWorld();
        if (world == null) return;

        if (matchedPattern == 0) {
            MythicBukkit.inst().getMobManager().spawnMob(mobId, location);
        } else if (matchedPattern == 1) {
            int num = mobNumSingle.getSingle(event);
            for (int i = 0; i < num; i++) {
                MythicBukkit.inst().getMobManager().spawnMob(mobId, location);
            }
        }

    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "spawn " + mobIdSingle.getSingle(event) +
                " at " + mobLocSingle.getSingle(event);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.matchedPattern = matchedPattern;
        if (matchedPattern == 0) {
            this.mobIdSingle = (Expression<String>) exprs[0];
            this.mobLocSingle = (Expression<Location>) exprs[1];
        } else if (matchedPattern == 1) {
            this.mobNumSingle = (Expression<Integer>) exprs[0];
            this.mobIdSingle = (Expression<String>) exprs[1];
            this.mobLocSingle = (Expression<Location>) exprs[2];
        }
        return true;
    }
}
