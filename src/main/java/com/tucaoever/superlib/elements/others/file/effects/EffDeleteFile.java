package com.tucaoever.superlib.elements.others.file.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import com.tucaoever.superlib.SUPERLIB;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by tim740 on 16/03/2016
 */
public class EffDeleteFile extends Effect {
    private Expression<String> path;
    private int ty;

    @Override
    protected void execute(Event e) {
        Path pth = Paths.get(SUPERLIB.getDefaultPath(path.getSingle(e)));
        if (ty == 0) {
            try {
                Files.delete(pth);
            } catch (Exception x) {
                SUPERLIB.log("File: '" + pth + "' doesn't exist!");
            }
        } else {
            try {
                Files.walkFileTree(pth, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path f, BasicFileAttributes attrs) throws IOException {
                        Files.delete(f);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path d, IOException exc) throws IOException {
                        Files.delete(d);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (Exception x) {
                SUPERLIB.log("Directory: '" + pth + "' doesn't exist!");
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
