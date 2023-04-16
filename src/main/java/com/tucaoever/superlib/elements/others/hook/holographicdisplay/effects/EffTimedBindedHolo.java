package com.tucaoever.superlib.elements.others.hook.holographicdisplay.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.tucaoever.superlib.SUPERLIB;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;

public class EffTimedBindedHolo extends Effect
{
    private Expression<String> text;
    private Expression<Timespan> time;
    private Expression<Entity> tar;
    private Expression<Number> xcord;
    private Expression<Number> ycord;
    private Expression<Number> zcord;
    public boolean init(final Expression<?>[] exp, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        this.text = (Expression<String>)exp[0];
        this.tar = (Expression<Entity>)exp[1];
        this.time = (Expression<Timespan>)exp[2];
        this.xcord = (Expression<Number>)exp[3];
        this.ycord = (Expression<Number>)exp[4];
        this.zcord = (Expression<Number>)exp[5];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return null;
    }
    
    protected void execute(final Event evt) {
        double hx = 0.0;
        double hy = 0.0;
        double hz = 0.0;
        if (this.xcord != null) {
            hx = ((Number)this.xcord.getSingle(evt)).doubleValue();
        }
        if (this.ycord != null) {
            hy = ((Number)this.ycord.getSingle(evt)).doubleValue();
        }
        if (this.zcord != null) {
            hz = ((Number)this.zcord.getSingle(evt)).doubleValue();
        }
        final Hologram hologram = HologramsAPI.createHologram((Plugin) SUPERLIB.getInstance(), ((Entity)this.tar.getSingle(evt)).getLocation());
        String core = ((String)this.text.getSingle(evt)).replace("\"", "");
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
                hologram.appendItemLine(stack);
            }
            else {
                hologram.appendTextLine(line);
            }
        }
        hologram.appendTextLine(core);
        final double fhx = hx;
        final double fhy = hy;
        final double fhz = hz;
        new BukkitRunnable() {
            int ticksRun;
            
            public void run() {
                ++this.ticksRun;
                hologram.teleport(((Entity)EffTimedBindedHolo.this.tar.getSingle(evt)).getLocation().add(fhx, fhy, fhz));
                if (this.ticksRun > ((Timespan)EffTimedBindedHolo.this.time.getSingle(evt)).getTicks()) {
                    hologram.delete();
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin) SUPERLIB.getInstance(), 1L, 1L);
    }
}
