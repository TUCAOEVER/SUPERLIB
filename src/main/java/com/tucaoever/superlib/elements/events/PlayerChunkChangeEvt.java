package com.tucaoever.superlib.elements.events;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.Chunk;
import org.bukkit.Bukkit;
import com.google.common.base.Objects;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.Listener;

public class PlayerChunkChangeEvt implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void chunkChangeDetect(final PlayerMoveEvent event) {
        final Chunk from = event.getFrom().getChunk();
        final Chunk to = event.getTo().getChunk();
        if (!Objects.equal(from, to)) {
            Bukkit.getServer().getPluginManager().callEvent(new EvtPlayerChunkChange(event.getPlayer(), from, to));
        }
    }
}
