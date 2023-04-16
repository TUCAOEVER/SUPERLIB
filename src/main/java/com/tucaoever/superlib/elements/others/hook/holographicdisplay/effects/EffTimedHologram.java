package com.tucaoever.superlib.elements.others.hook.holographicdisplay.effects;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.Bukkit;
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
import org.bukkit.Location;
import ch.njol.skript.util.Timespan;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Effect;

public class EffTimedHologram extends Effect
{
    private Expression<String> text;
    private Expression<Timespan> time;
    private Expression<Location> loc;
    
    public boolean init(final Expression<?>[] exp, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        this.text = (Expression<String>)exp[0];
        this.loc = (Expression<Location>)exp[1];
        this.time = (Expression<Timespan>)exp[2];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return null;
    }
    
    protected void execute(final Event evt) {
        final Hologram hologram = HologramsAPI.createHologram((Plugin) SUPERLIB.getInstance(), (Location)this.loc.getSingle(evt));
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
        Bukkit.getScheduler().runTaskLater((Plugin) SUPERLIB.getInstance(), (Runnable)new Runnable() {
            @Override
            public void run() {
                hologram.delete();
            }
        }, (long)((Timespan)this.time.getSingle(evt)).getTicks());
    }
}
