package com.tucaoever.superlib.elements.listeners;

import com.tucaoever.superlib.elements.events.EvtChunkChange;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ChunkChangeListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMove(final PlayerMoveEvent event) {
        Chunk from = event.getFrom().getChunk();
        Chunk to = event.getTo().getChunk();
        if (!from.equals(to))
            Bukkit.getServer().getPluginManager()
                    .callEvent(new EvtChunkChange(event.getPlayer(), from, to));
    }
}
