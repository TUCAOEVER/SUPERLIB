package com.tucaoever.superlib.elements.others.hook.holographicdisplay;

import ch.njol.skript.Skript;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.tucaoever.superlib.SUPERLIB;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class HoloManager {
    public static HashMap<String, Hologram> holomap;

    static {
        HoloManager.holomap = new HashMap<String, Hologram>();
    }

    public static boolean addToHoloMap(final String id, final Hologram holo) {
        if (HoloManager.holomap.containsKey(id)) {
            Skript.error("A hologram with the id " + id + " already exists!");
            return true;
        }
        HoloManager.holomap.put(id, holo);
        return false;
    }

    public static boolean isInHoloMap(final String id) {
        return HoloManager.holomap.containsKey(id);
    }

    public static Hologram removeFromHoloMap(final String id) {
        if (HoloManager.holomap.containsKey(id)) {
            final Hologram holo = HoloManager.holomap.get(id);
            HoloManager.holomap.remove(id);
            return holo;
        }
        return null;
    }

    public static Hologram getFromHoloMap(final String id) {
        if (HoloManager.holomap.containsKey(id)) {
            return HoloManager.holomap.get(id);
        }
        return null;
    }

    public static void editHoloMap(final String id, final Hologram holo) {
        if (HoloManager.holomap.containsKey(id)) {
            HoloManager.holomap.remove(id);
            HoloManager.holomap.put(id, holo);
        }
    }

    public static void dumpHoloMap() {
        for (final Hologram h : HoloManager.holomap.values()) {
            h.delete();
        }
        HoloManager.holomap.clear();
    }

    public static void followEntity(final Entity ent, final String id, final double xoffset, final double yoffset, final double zoffset) {
        new BukkitRunnable() {
            public void run() {
                final Hologram hologram = HoloManager.getFromHoloMap(id);
                if (HoloManager.isInHoloMap(id)) {
                    hologram.teleport(ent.getLocation().add(xoffset, yoffset, zoffset));
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(SUPERLIB.getInstance(), 1L, 1L);
    }
}
