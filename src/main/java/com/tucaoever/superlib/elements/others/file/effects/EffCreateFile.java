package com.tucaoever.superlib.elements.others.file.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.SUPERLIB;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EffCreateFile extends Effect {
    private Expression<String> path;
    private int ty;

    @Override
    protected void execute(Event e) {
        Path pth = Paths.get(SUPERLIB.getDefaultPath(path.getSingle(e)));
        if (ty == 0) {
            try {
                Path fwn = Paths.get(pth.toString().replace(File.separator + pth.toString().substring(pth.toString().lastIndexOf(File.separator) + 1), ""));
                if (!Files.exists(fwn)) {
                    Files.createDirectories(fwn);
                }
                Files.createFile(pth);
            } catch (Exception x) {
                SUPERLIB.log("File: '" + pth + "' already exists!");
            }
        } else {
            try {
                Files.createDirectories(pth);
            } catch (Exception x) {
                SUPERLIB.log("Directory: '" + pth + "' already exists!");
            }

        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] e, int i, Kleenean k, SkriptParser.ParseResult p) {
        path = (Expression<String>) e[0];
        ty = p.mark;
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return getClass().getName();
    }
}