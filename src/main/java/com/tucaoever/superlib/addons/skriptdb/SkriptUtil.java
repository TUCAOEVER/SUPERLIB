package com.tucaoever.superlib.addons.skriptdb;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.VariableString;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public class SkriptUtil {

    private static final Field STRING;

    static {
        Field _FIELD = null;
        try {
            _FIELD = VariableString.class.getDeclaredField("string");
            _FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Skript.error("Skript's 'string' field could not be resolved.");
            e.printStackTrace();
        }
        STRING = _FIELD;
    }

    public static Object[] getTemplateString(VariableString vs) {
        try {
            return (Object[]) STRING.get(vs);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
