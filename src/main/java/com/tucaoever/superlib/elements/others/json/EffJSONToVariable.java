package com.tucaoever.superlib.elements.others.json;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ch.njol.skript.variables.Variables;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.util.Locale;
import org.bukkit.event.Event;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Effect;

public class EffJSONToVariable extends Effect
{
    private Expression<String> json;
    private VariableString var;
    private boolean isLocal;
    
    protected void execute(final Event e) {
        final String json = (String)this.json.getSingle(e);
        final String var = this.var.toString(e).toLowerCase(Locale.ENGLISH);
        if (json == null) {
            return;
        }
        try {
            final Object parsed = new JSONParser().parse(json);
            this.mapFirst(e, var.substring(0, var.length() - 3), parsed);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    
    private void setVariable(final Event e, final String name, final Object obj) {
        Variables.setVariable(name.toLowerCase(Locale.ENGLISH), obj, e, this.isLocal);
    }
    
    private void mapFirst(final Event e, final String name, final Object obj) {
        if (obj instanceof JSONObject) {
            this.handleObject(e, name, (JSONObject)obj);
        }
        else if (obj instanceof JSONArray) {
            this.handleArray(e, name, (JSONArray)obj);
        }
        else {
            this.setVariable(e, name, obj);
        }
    }
    
    private void map(final Event e, final String name, final Object obj) {
        if (obj instanceof JSONObject) {
            if (((JSONObject)obj).containsKey((Object)"__javaclass__") || ((JSONObject)obj).containsKey((Object)"__skriptclass__")) {
                this.setVariable(e, name, Serializers.deserialize((JSONObject)obj));
            }
            else {
                this.setVariable(e, name, true);
                this.handleObject(e, name, (JSONObject)obj);
            }
        }
        else if (obj instanceof JSONArray) {
            this.setVariable(e, name, true);
            this.handleArray(e, name, (JSONArray)obj);
        }
        else {
            this.setVariable(e, name, obj);
        }
    }
    
    private void handleObject(final Event e, final String name, final JSONObject obj) {
        obj.keySet().forEach(key -> this.map(e, String.valueOf(name) + "::" + key, obj.get(key)));
    }
    
    private void handleArray(final Event e, final String name, final JSONArray obj) {
        for (int i = 0; i < obj.size(); ++i) {
            this.map(e, String.valueOf(name) + "::" + (i + 1), obj.get(i));
        }
    }
    
    public String toString(@Nullable final Event e, final boolean debug) {
        return String.valueOf(this.json.toString(e, debug)) + " => " + this.var.toString(e, debug);
    }
    
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        this.json = (Expression<String>)exprs[0];
        final Expression<?> expr = exprs[1];
        if (expr instanceof Variable) {
            final Variable<?> varExpr = (Variable<?>)expr;
            if (varExpr.isList()) {
                this.var = SkriptUtil.getVariableName(varExpr);
                this.isLocal = varExpr.isLocal();
                return true;
            }
        }
        Skript.error(expr + " is not a list variable");
        return false;
    }
}
