package com.tucaoever.superlib.elements.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.registrations.Classes;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExprTabCompletion extends SimpleExpression<String> {

    private Expression<Number> position;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(@NotNull Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(TabCompleteEvent.class)) {
            Skript.error("Tab completions are only usable in a tab complete event.", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        position = (Expression<Number>) exprs[0];
        return true;
    }

    @Nullable
    @Override
    protected String[] get(@NotNull Event event) {
        return ((TabCompleteEvent) event).getCompletions().toArray(new String[0]);
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(@NotNull ChangeMode mode) {
        switch (mode) {
            case SET:
            case REMOVE:
            case DELETE:
            case ADD:
            case REMOVE_ALL:
                return CollectionUtils.array(Object[].class);
        }
        return null;
    }

    @Override
    public void change(@NotNull Event e, @Nullable Object[] objects, @NotNull ChangeMode mode) {
        TabCompleteEvent event = ((TabCompleteEvent) e);

        Number number = this.position.getSingle(e);
        int position = 1;
        if (number != null) {
            position = number.intValue();
        }

        switch (mode) {
            case SET:
                String buff = event.getBuffer();
                String[] buffers = buff.split(" ");
                String last = buff.substring(buff.length() - 1);
                if ((position == buffers.length && last.equalsIgnoreCase(" ")) ||
                        (position + 1 == buffers.length && !last.equalsIgnoreCase(" "))) {
                    String arg;
                    if (position == buffers.length) {
                        arg = "";
                    } else {
                        arg = buffers[position];
                    }

                    List<String> completions = new ArrayList<>();
                    if (objects == null) {
                        event.setCompletions(Collections.singletonList(""));
                        return;
                    }
                    for (Object o : objects) {
                        String object = Classes.toString(o);
                        if (StringUtil.startsWithIgnoreCase(object, arg)) {
                            completions.add(object);
                        }
                    }
                    event.setCompletions(completions);
                }
                break;
            case REMOVE:
                assert objects != null;
                for (Object object : objects) {
                    try {
                        event.getCompletions().remove(object.toString());
                    } catch (Exception ignore) {
                    } // Had a little issue when removing from a blank list
                }
                break;
            case ADD:
                assert objects != null;
                for (Object object : objects) {
                    try {
                        event.getCompletions().add(Classes.toString(object));
                    } catch (Exception ignore) {
                    } // Had a little issue when adding if none were there yet
                }
                break;
            case DELETE:
                event.setCompletions(Collections.singletonList(""));
                break;
        }
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public @NotNull Class<String> getReturnType() {
        return String.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean d) {
        String pos = this.position != null ? " for position " + this.position.toString(e, d) : "";
        return "tab completions" + pos;
    }

}