package com.tucaoever.superlib.elements.others.file.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.SUPERLIB;
import org.bukkit.event.Event;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class CondFileExists extends Condition {
    private Expression<String> path;

    @Override
    public boolean check(Event e) {
        try {
            boolean pth = Files.exists(Paths.get(SUPERLIB.getDefaultPath(path.getSingle(e))));
            return (isNegated() != pth);
        } catch (InvalidPathException i){
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean k, SkriptParser.ParseResult p) {
        path = (Expression<String>) e[0];
        setNegated(i == 1);
        return true;
    }

    @Override
    public String toString(Event e, boolean b) {
        return getClass().getName();
    }
}