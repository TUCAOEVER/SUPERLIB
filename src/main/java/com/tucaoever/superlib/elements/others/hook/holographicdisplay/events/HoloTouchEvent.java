package com.tucaoever.superlib.elements.others.hook.holographicdisplay.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Event;

public class HoloTouchEvent extends Event
{
    private static final HandlerList handlers;
    private Player player;
    private String hologramId;
    private int line;
    
    static {
        handlers = new HandlerList();
    }
    
    public HandlerList getHandlers() {
        return HoloTouchEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return HoloTouchEvent.handlers;
    }
    
    public HoloTouchEvent(final Player player, final String hologramId, final int lineNumber) {
        this.player = player;
        this.hologramId = hologramId;
        this.line = lineNumber;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public String getHoloId() {
        return this.hologramId;
    }
    
    public int getLineNumber() {
        return this.line;
    }
}
