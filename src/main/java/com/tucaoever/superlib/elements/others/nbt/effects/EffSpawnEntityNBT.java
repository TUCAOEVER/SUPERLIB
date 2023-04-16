package com.tucaoever.superlib.elements.others.nbt.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.entity.EntityType;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Direction;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.SUPERLIB;
import com.tucaoever.superlib.util.NBT.NBTApi;
import com.tucaoever.superlib.util.SkriptUtils;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Name("NBT - Spawn Entity with NBT")
@Description("Spawn an entity at a location with NBT")
@Examples({"spawn sheep at player with nbt \"{NoAI:1b}\"",
        "spawn 1 of zombie at player with nbt \"{NoGravity:1b}\""})
@Since("1.0.0")
public class EffSpawnEntityNBT extends Effect {

    private static final NBTApi NBT_API = SUPERLIB.getInstance().getNbtApi();

    @SuppressWarnings("null")
    private Expression<Location> locations;
    @SuppressWarnings("null")
    private Expression<EntityType> types;
    private Expression<Object> nbt;
    @Nullable
    private Expression<Number> amount;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parser) {
        amount = matchedPattern == 0 ? null : (Expression<Number>) (exprs[0]);
        types = (Expression<EntityType>) exprs[matchedPattern];
        locations = Direction.combine((Expression<? extends Direction>) exprs[1 + matchedPattern], (Expression<? extends Location>) exprs[2 + matchedPattern]);
        nbt = (Expression<Object>) exprs[3 + matchedPattern];
        return true;
    }

    @Override
    public void execute(final @NotNull Event event) {
        Object nbtObject = this.nbt.getSingle(event);
        String value = nbtObject instanceof NBTCompound ? nbtObject.toString() : (String) nbtObject;
        final Number a = amount != null ? amount.getSingle(event) : 1;
        if (a == null)
            return;
        final EntityType[] et = types.getArray(event);
        for (final Location loc : locations.getArray(event)) {
            assert loc != null : locations;
            for (final EntityType type : et) {
                for (int i = 0; i < a.doubleValue() * type.getAmount(); i++) {
                    spawn(loc, type.data.getType(), value);
                }
            }
        }
    }

    @Override
    public @NotNull String toString(Event e, boolean debug) {
        return "spawn " + (amount != null ? amount.toString(e, debug) + " " : "") + types.toString(e, debug) +
                " " + locations.toString(e, debug) + " " + nbt.toString(e, debug);
    }

    private <T extends Entity> void spawn(Location loc, Class<T> type, String nbt) {
        Entity entity = loc.getWorld().spawn(loc, type, ent -> NBT_API.addNBT(ent, nbt, NBTApi.ObjectType.ENTITY));
        SkriptUtils.setLastSpawned(entity);
    }

}
