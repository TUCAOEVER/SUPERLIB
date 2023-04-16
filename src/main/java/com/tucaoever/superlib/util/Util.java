package com.tucaoever.superlib.util;

import java.util.UUID;

public class Util {

    public static void debug(String format, Object... objects) {
        debug(String.format(format, objects));
    }

    /**
     * Convert a UUID to an int array
     * <p>Used for Minecraft 1.16+</p>
     *
     * @param uuid String UUID to convert
     * @return int array from UUID
     */
    public static int[] uuidToIntArray(String uuid) {
        return uuidToIntArray(UUID.fromString(uuid));
    }

    /**
     * Convert a UUID to an int array
     * <p>Used for Minecraft 1.16+</p>
     *
     * @param uuid UUID to convert
     * @return int array from UUID
     */
    public static int[] uuidToIntArray(UUID uuid) {
        long most = uuid.getMostSignificantBits();
        long least = uuid.getLeastSignificantBits();
        return new int[]{(int) (most >> 32), (int) most, (int) (least >> 32), (int) least};
    }
}