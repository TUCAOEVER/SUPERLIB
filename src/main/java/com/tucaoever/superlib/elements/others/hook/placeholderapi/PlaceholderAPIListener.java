package com.tucaoever.superlib.elements.others.hook.placeholderapi;

import com.tucaoever.superlib.SUPERLIB;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderAPIListener extends PlaceholderExpansion {

    private SUPERLIB plugin;
    private String prefix;

    public PlaceholderAPIListener(SUPERLIB plugin, String prefix) {
        this.plugin = plugin;
        this.prefix = prefix;
    }

    @Override
    public String getIdentifier() {
        return prefix;
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        PlaceholderAPIEvent event = new PlaceholderAPIEvent(identifier, player, prefix);
        Bukkit.getPluginManager().callEvent(event);
        return event.getResult();
    }

}

