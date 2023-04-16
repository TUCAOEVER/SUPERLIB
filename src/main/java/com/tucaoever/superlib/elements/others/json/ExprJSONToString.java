package com.tucaoever.superlib.elements.others.json;

import org.bukkit.event.Event;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;

public class ExprJSONToString extends SimpleExpression<String> {

    private VariableString var;
    private boolean isLocal;

    @Override
    protected String[] get(Event e) {
        String var = this.var.toString(e).toLowerCase(Locale.ENGLISH);

        return new String[] {getTree(e, var.substring(0, var.length() - 1), false).toJSONString()};
    }

    private Object getVariable(Event e, String name) {
        final Object val = Variables.getVariable(name, e, isLocal);
        if (val == null) {
            return Variables.getVariable((isLocal ? Variable.LOCAL_VARIABLE_TOKEN : "") + name, e, false);
        }
        return val;
    }

    private static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        while (i < length) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
            i++;
        }
        return true;
    }

    private Object getSubtree(Event e, String name) {
        Object val = getVariable(e, name);

        if (val == null) {
            val = getTree(e, name + Variable.SEPARATOR, false);
        } else if (val == Boolean.TRUE) {
            Object subtree = getTree(e, name + Variable.SEPARATOR, true);
            if (subtree != null) {
                val = subtree;
            }
        }

        if (!(val instanceof String || val instanceof Number || val instanceof Boolean
                || val instanceof JSONAware|| val instanceof Map || val instanceof List)) {
            val = Serializers.serialize(val);
        }

        return val;
    }

    @SuppressWarnings("unchecked")
    private JSONAware getTree(Event e, String name, boolean nullable) {
        Map<String, Object> var = (Map<String, Object>) getVariable(e, name + "*");

        if (var == null) {
            return nullable ? null : new JSONObject();
        }

        Stream<String> keys = var.keySet().stream().filter(Objects::nonNull);

        if (var.keySet().stream().filter(Objects::nonNull).allMatch(ExprJSONToString::isInteger)) {
            JSONArray obj = new JSONArray();

            keys.forEach(key -> obj.add(getSubtree(e, name + key)));

            return obj;
        } else {
            JSONObject obj = new JSONObject();

            keys.forEach(key -> obj.put(key, getSubtree(e, name + key)));

            return obj;
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed,
                        SkriptParser.ParseResult parseResult) {
        Expression<?> expr = exprs[0];
        if (expr instanceof Variable) {
            Variable<?> varExpr = (Variable<?>) expr;
            if (varExpr.isList()) {
                var = SkriptUtil.getVariableName(varExpr);
                isLocal = varExpr.isLocal();
                return true;
            }
        }
        Skript.error(expr + " is not a list variable");
        return false;
    }
}