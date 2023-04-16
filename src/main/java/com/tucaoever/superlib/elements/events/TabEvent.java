package com.tucaoever.superlib.elements.events;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import org.bukkit.event.Event;
import org.bukkit.event.server.TabCompleteEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;

public class TabEvent extends SkriptEvent {

    private String[] commands;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(@NotNull Literal<?>[] args, int matchedPattern, @NotNull ParseResult parseResult) {
        commands = args[0] == null ? null : ((Literal<String>)args[0]).getAll();
        return true;
    }

    @Override
    public boolean check(@NotNull Event event) {
        if (commands == null) return true;

        TabCompleteEvent tabEvent = ((TabCompleteEvent) event);
        String command = tabEvent.getBuffer().split(" ")[0];
        if (command.charAt(0) == '/') {
            command = command.substring(1);
        }
        for (String s : commands) {
            if (s.charAt(0) == '/') {
                s = s.substring(1);
            }
            if (s.equalsIgnoreCase(command)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean d) {
        return "tab complete" + (commands == null ? "" : " for " + Arrays.toString(commands));
    }

}