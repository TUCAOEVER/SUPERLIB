package com.tucaoever.superlib.util.NBT;

import com.tucaoever.superlib.SUPERLIB;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import org.bukkit.NamespacedKey;

public interface NBTCustom {

    NamespacedKey OLD_KEY = new NamespacedKey(SUPERLIB.getInstance(), "custom-nbt");

    NBTCompound getCustomNBT();

    void deleteCustomNBT();

}
