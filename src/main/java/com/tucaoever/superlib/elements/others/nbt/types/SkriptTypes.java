package com.tucaoever.superlib.elements.others.nbt.types;

import ch.njol.skript.classes.Changer;
import ch.njol.util.coll.CollectionUtils;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"unused", "NullableProblems"})
public class SkriptTypes {

    public static final Changer<NBTCompound> NBT_COMPOUND_CHANGER = new Changer<NBTCompound>() {
        @Nullable
        @Override
        public Class<?>[] acceptChange(ChangeMode mode) {
            if (mode == ChangeMode.ADD) {
                return CollectionUtils.array(NBTCompound.class);
            }
            return null;
        }

        @Override
        public void change(NBTCompound[] what, @Nullable Object[] delta, ChangeMode mode) {
            if (delta[0] instanceof NBTCompound) {
                NBTCompound changer = (NBTCompound) delta[0];

                if (mode == ChangeMode.ADD) {
                    for (NBTCompound nbtCompound : what) {
                        nbtCompound.mergeCompound(changer);
                    }
                }
            }
        }
    };

}
