package com.tucaoever.superlib.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.WrapperExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.iterator.ArrayIterator;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Name("SubList")
@Description("returns part of a list")
@Examples("set {a::*} to first 5 elements of {b::*}")
@Since("2.0.0")

@SuppressWarnings("unchecked")
public class ExprSubList<T> extends WrapperExpression<T> {
    private Expression<Number> numberExpr1, numberExpr2;
    private int pattern;

    @Override
    protected T @NotNull [] get(@NotNull Event e) {
        Number n1 = numberExpr1.getSingle(e), n2 = numberExpr2 == null ? null : numberExpr2.getSingle(e);
        T[] array = super.get(e);
        return switch (pattern) {
            case 0 ->
                    (n1 == null || n2 == null) ? null : Arrays.copyOfRange(array, max(n1.intValue(), 0), max(n2.intValue(), 0));
            case 1 -> n1 == null ? null : Arrays.copyOf(array, min(max(n1.intValue(), 0), array.length));
            case 2 ->
                    n1 == null ? null : Arrays.copyOfRange(array, array.length - min(max(n1.intValue(), 0), array.length), array.length);
            default -> null;
        };
    }

    @Override
    public @Nullable Iterator<? extends T> iterator(@NotNull Event e) {
        return new ArrayIterator<>(get(e));
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return pattern == 0 ? "sublist of " + getExpr().toString(e, debug) + " from index " + numberExpr1.toString(e, debug) + " to " + numberExpr2.toString(e, debug) :
                (pattern == 1 ? "first " : "last ") + numberExpr1.toString(e, debug) + " elements of " + getExpr().toString(e, debug);
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
        setExpr((Expression<? extends T>) exprs[matchedPattern]);
        if (matchedPattern == 0) {
            numberExpr1 = (Expression<Number>) exprs[1];
            numberExpr2 = (Expression<Number>) exprs[2];
        } else
            numberExpr1 = (Expression<Number>) exprs[0];
        pattern = matchedPattern + parseResult.mark;
        return true;
    }
}