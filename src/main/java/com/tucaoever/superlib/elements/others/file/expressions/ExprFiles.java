package com.tucaoever.superlib.elements.others.file.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExprFiles extends SimpleExpression<String> {
    private Expression<String> dirExpr;

    @Override
    protected String[] get(Event event) {
        Path dir = Paths.get(this.dirExpr.getSingle(event));
        try {
            if (Files.notExists(dir)) {
                Files.createDirectories(dir);
                return null;
            }
            String[] res = Files.list(dir)
                .filter(Files::isRegularFile)
                .map(path -> path.getFileName().toString())
                .toArray(String[]::new);
            return res.length > 0 ? res : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
        this.dirExpr = (Expression<String>) expressions[0];
        return true;
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
    public String toString(Event event, boolean b) {
        return this.getClass().toString();
    }
}
