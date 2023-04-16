package com.tucaoever.superlib.elements.events;

import org.bukkit.event.HandlerList;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EvtPlayerChunkChange extends Event
{
    Player player;
    Chunk from;
    Chunk to;
    private static final HandlerList handlers;

    static {
        handlers = new HandlerList();
    }

    public EvtPlayerChunkChange(final Player player, final Chunk from, final Chunk to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Chunk getFrom() {
        return this.from;
    }

    public Chunk getTo() {
        return this.to;
    }

    public HandlerList getHandlers() {
        return EvtPlayerChunkChange.handlers;
    }

    public static HandlerList getHandlerList() {
        return EvtPlayerChunkChange.handlers;
    }
}