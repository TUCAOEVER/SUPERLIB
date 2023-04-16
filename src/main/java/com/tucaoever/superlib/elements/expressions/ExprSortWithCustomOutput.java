package com.tucaoever.superlib.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.Pair;
import com.google.common.collect.Lists;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ExprSortWithCustomOutput extends SimpleExpression<String> {

    private Variable variableList;
    private Expression<String> format;
    private int pattern;

    public Class<? extends String> getReturnType() {
        return String.class;
    }

    public boolean isSingle() {
        return false;
    }

    public boolean init(Expression<?>[] expr, int pattern, Kleenean kl, SkriptParser.ParseResult pr) {
        try {
            this.variableList = ((Variable)expr[0]);
        }
        catch (ClassCastException e) {
            Skript.error("You must put values into variable list that you want to sort because this expression reads index and value from variable.");
            return false;
        }
        this.format = (Expression<String>)expr[1];
        this.pattern = pattern;
        return true;
    }

    public String toString(Event event, boolean debug) {
        if (pattern == 0){
            return "soreted " + variableList.toString(event, debug) +
                    "from highest to lowest with format " + format.toString(event, debug);
        }
        return "soreted " + variableList.toString(event, debug) +
                "from lowest to highest with format " + format.toString(event, debug);
    }

    protected String[] get(Event e) {
        String customFormat = this.format.getSingle(e);
        if ((this.variableList == null) || (customFormat == null)) {
            return null;
        }
        Iterator<Pair<String, Object>> iterator = this.variableList.variablesIterator(e);
        // Convert all values and indexes into pair list.
        List<Pair<String, Object>> pairList = new ArrayList<>();
        while (iterator.hasNext()){
            pairList.add(iterator.next());
        }
        // Sort the list by variable values.
        pairList.sort(Comparator.comparing(p -> Double.valueOf(p.getValue().toString())));
        // Convert sorted pair list into custom text output list.
        List<String> customOutputList = new ArrayList<>();
        for (Pair<String, Object> variableContent : pairList){
            customOutputList.add(customFormat
                    .replace("@value", variableContent.getValue()+"")
                    .replace("@index", variableContent.getKey())
            );
        }
        // Return outputs with correct order
        if (this.pattern == 0) { // From hight to low
            return Lists.reverse(customOutputList).toArray(new String[customOutputList.size()]); // Defaultly, sort is going from begining to end of something so it must be reversed.
        } else if (this.pattern == 1) { // From low to hight
            return customOutputList.toArray(new String[0]);
        }
        return null;
    }
}