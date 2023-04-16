package com.tucaoever.superlib.elements.others.json;

import ch.njol.skript.lang.VariableString;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Variable;
import java.lang.reflect.Field;

public class SkriptUtil
{
    private static final Field VARIABLE_NAME;
    
    static {
        Field _VARIABLE_NAME = null;
        try {
            _VARIABLE_NAME = Variable.class.getDeclaredField("name");
            _VARIABLE_NAME.setAccessible(true);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
            Skript.error("Skript's 'variable name' method could not be resolved.");
        }
        VARIABLE_NAME = _VARIABLE_NAME;
    }
    
    public static VariableString getVariableName(final Variable<?> var) {
        try {
            return (VariableString)SkriptUtil.VARIABLE_NAME.get(var);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
