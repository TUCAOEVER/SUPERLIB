package com.tucaoever.superlib.elements.events.listener;

import com.tucaoever.superlib.elements.events.EvtPlayerChunkChange;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerChunkChangeListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMove(final PlayerMoveEvent event) {
        Chunk from = event.getFrom().getChunk();
        Chunk to = event.getTo().getChunk();
        if (!from.equals(to))
            Bukkit.getServer().getPluginManager()
                    .callEvent(new EvtPlayerChunkChange(event.getPlayer(), from, to));
    }
}
