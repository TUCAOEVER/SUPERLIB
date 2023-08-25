package com.tucaoever.superlib.elements.others.hook.placeholderapi;

import com.tucaoever.superlib.SUPERLIB;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIListener extends PlaceholderExpansion {

    private final SUPERLIB plugin;
    private final String prefix;

    public PlaceholderAPIListener(SUPERLIB plugin, String prefix) {
        this.plugin = plugin;
        this.prefix = prefix;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return prefix;
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        PlaceholderAPIEvent event = new PlaceholderAPIEvent(identifier, player, prefix);
        Bukkit.getPluginManager().callEvent(event);
        return event.getResult();
    }

}

