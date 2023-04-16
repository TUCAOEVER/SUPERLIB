package com.tucaoever.superlib.elements.others.hook.worldguard.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.managers.storage.StorageException;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Map.Entry;

public class EffManageOwner extends Effect {

    private Expression<Player> players;
    private Expression<String> name;
    private Expression<World> world;
    private int action;

    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.players = (Expression<Player>) expressions[0];
        this.name = (Expression<String>) expressions[1];
        this.world = (Expression<World>) expressions[2];
        action = parseResult.mark;
        return true;
    }

    protected void execute(Event event) {
        String name = this.name.getSingle(event);
        World world = this.world.getSingle(event);
        WorldGuardPlatform worldGuardPlatform = WorldGuard.getInstance().getPlatform();

        if (world == null) {
            for (RegionManager regionManager : worldGuardPlatform.getRegionContainer().getLoaded()) {
                for (Entry<String, ProtectedRegion> protectedRegionEntry : regionManager.getRegions().entrySet()) {
                    if (protectedRegionEntry.getKey().equals(name)) {
                        world = Bukkit.getWorld(regionManager.getName());
                        break;
                    }
                }
            }
        }

        RegionManager regionManager = worldGuardPlatform.getRegionContainer().get(BukkitAdapter.adapt(world));
        if (regionManager == null) {
            Skript.error("Region \"" + name + "\" in world \"" + world.getName() + "\" does not exists.");
            return;
        }

        if (!regionManager.hasRegion(name)) {
            Skript.error("Region \"" + name + "\" in world \"" + world.getName() + "\" does not exists.");
        } else {
            ProtectedRegion region = regionManager.getRegion(name);
            DefaultDomain owners = region.getOwners();
            if (action == 0)
                for (Player player : this.players.getArray(event)) {
                    owners.addPlayer(player.getUniqueId());
                }
            else if (action == 1) {
                for (Player player : this.players.getArray(event)) {
                    owners.removePlayer(player.getUniqueId());
                }
            }
            try {
                regionManager.saveChanges();
                regionManager.load();
            } catch (StorageException e) {
                e.printStackTrace();
            }
        }
    }

    public String toString(Event e, boolean debug) {
        return getClass().getName();
    }
}