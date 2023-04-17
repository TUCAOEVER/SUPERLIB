package com.tucaoever.superlib.register;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.tucaoever.superlib.SUPERLIB;
import com.tucaoever.superlib.elements.events.EvtPlayerChunkChange;
import com.tucaoever.superlib.elements.events.listener.PlayerChunkChangeListener;
import com.tucaoever.superlib.elements.events.TabEvent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.server.TabCompleteEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Events {
    public static void register() {
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerChunkChangeListener(), SUPERLIB.getInstance());
        Skript.registerEvent("chunk change", SimpleEvent.class, EvtPlayerChunkChange.class,
                "chunk change");
        EventValues.registerEventValue(EvtPlayerChunkChange.class, Player.class,
                new Getter<Player, EvtPlayerChunkChange>() {
                    public Player get(final EvtPlayerChunkChange e) {
                        return e.getPlayer();
                    }
                }, 0);
        EventValues.registerEventValue(EvtPlayerChunkChange.class, Chunk.class,
                new Getter<Chunk, EvtPlayerChunkChange>() {
                    public Chunk get(final EvtPlayerChunkChange e) {
                        return e.getFrom();
                    }
                }, 0);
        EventValues.registerEventValue(EvtPlayerChunkChange.class, Chunk.class,
                new Getter<Chunk, EvtPlayerChunkChange>() {
                    public Chunk get(final EvtPlayerChunkChange e) {
                        return e.getTo();
                    }
                }, 0);

        Skript.registerEvent("Tab Complete", TabEvent.class, TabCompleteEvent.class,
                "tab complete [(of|for) %strings%]");
        EventValues.registerEventValue(TabCompleteEvent.class, Player.class, new Getter<Player, TabCompleteEvent>() {
            @Nullable
            @Override
            public Player get(@NotNull TabCompleteEvent event) {
                CommandSender sender = event.getSender();
                if (sender instanceof Player) {
                    return ((Player) sender).getPlayer();
                }
                return null;
            }
        }, 0);
        EventValues.registerEventValue(TabCompleteEvent.class, String.class, new Getter<String, TabCompleteEvent>() {
            @Nullable
            @Override
            public String get(@NotNull TabCompleteEvent event) {
                return event.getBuffer().split(" ")[0];
            }
        }, 0);
    }
}
