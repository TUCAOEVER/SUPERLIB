package com.tucaoever.superlib.elements.others.hook.holographicdisplay.effects;

import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.handler.PickupHandler;
import com.tucaoever.superlib.elements.others.hook.holographicdisplay.HoloManager;
import com.tucaoever.superlib.elements.others.hook.holographicdisplay.events.HoloPickupEvent;
import com.tucaoever.superlib.elements.others.hook.holographicdisplay.events.HoloTouchEvent;
import org.bukkit.Bukkit;
import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import ch.njol.skript.Skript;
import org.bukkit.plugin.Plugin;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.tucaoever.superlib.SUPERLIB;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Effect;

public class EffCreateInteractiveStaticClientSideHolograms extends Effect
{
    private Expression<String> text;
    private Expression<String> id;
    private Expression<Location> loc;
    private Expression<Player> player;
    
    public boolean init(final Expression<?>[] exp, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        this.id = (Expression<String>)exp[1];
        this.text = (Expression<String>)exp[0];
        this.loc = (Expression<Location>)exp[2];
        this.player = (Expression<Player>)exp[3];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return null;
    }
    
    protected void execute(final Event evt) {
        final Hologram hologram = HologramsAPI.createHologram((Plugin) SUPERLIB.getInstance(), (Location)this.loc.getSingle(evt));
        final VisibilityManager visibilityManager = hologram.getVisibilityManager();
        visibilityManager.showTo((Player)this.player.getSingle(evt));
        visibilityManager.setVisibleByDefault(false);
        String core = ((String)this.text.getSingle(evt)).replace("\"", "");
        int lineNumber = 1;
        while (core.indexOf(";") != -1) {
            String line = core.substring(0, core.indexOf(";"));
            core = core.substring(core.indexOf(";") + 1);
            if (line.startsWith("ItemStack:")) {
                line = line.substring(line.indexOf(":") + 1);
                int meta = 0;
                if (line.contains(":")) {
                    try {
                        meta = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                    }
                    catch (NumberFormatException exception) {
                        Skript.error("Meta data could not be parsed correctly!");
                        continue;
                    }
                    line = line.substring(0, line.indexOf(":"));
                }
                ItemStack stack = new ItemStack(Material.AIR, 1);
                try {
                    final Material mat = Material.valueOf(line.toUpperCase().replace(" ", "_"));
                    stack = new ItemStack(mat, 1);
                    if (meta != 0) {
                        stack = new ItemStack(mat, 1, (short)(byte)meta);
                    }
                }
                catch (IllegalArgumentException exception2) {
                    Skript.error("A item under that name does not exsist!");
                    continue;
                }
                final ItemLine itemline = hologram.appendItemLine(stack);
                final int templine = lineNumber;
                itemline.setTouchHandler((TouchHandler)new TouchHandler() {
                    public void onTouch(final Player player) {
                        final HoloTouchEvent event = new HoloTouchEvent(player, ((String)EffCreateInteractiveStaticClientSideHolograms.this.id.getSingle(evt)).replace("\"", ""), templine);
                        Bukkit.getPluginManager().callEvent((Event)event);
                    }
                });
                itemline.setPickupHandler((PickupHandler)new PickupHandler() {
                    public void onPickup(final Player player) {
                        final HoloPickupEvent event = new HoloPickupEvent(player, ((String)EffCreateInteractiveStaticClientSideHolograms.this.id.getSingle(evt)).replace("\"", ""), templine);
                        Bukkit.getPluginManager().callEvent((Event)event);
                    }
                });
                ++lineNumber;
            }
            else {
                final TextLine textline = hologram.appendTextLine(line);
                final int templine2 = lineNumber;
                textline.setTouchHandler((TouchHandler)new TouchHandler() {
                    public void onTouch(final Player player) {
                        final HoloTouchEvent event = new HoloTouchEvent(player, ((String)EffCreateInteractiveStaticClientSideHolograms.this.id.getSingle(evt)).replace("\"", ""), templine2);
                        Bukkit.getPluginManager().callEvent((Event)event);
                    }
                });
                ++lineNumber;
            }
        }
        final TextLine textline2 = hologram.appendTextLine(core);
        final int templine3 = lineNumber;
        textline2.setTouchHandler((TouchHandler)new TouchHandler() {
            public void onTouch(final Player player) {
                final HoloTouchEvent event = new HoloTouchEvent(player, ((String)EffCreateInteractiveStaticClientSideHolograms.this.id.getSingle(evt)).replace("\"", ""), templine3);
                Bukkit.getPluginManager().callEvent((Event)event);
            }
        });
        if (HoloManager.addToHoloMap(((String) this.id.getSingle(evt)).replace("\"", ""), hologram)) {
            hologram.delete();
        }
    }
}
