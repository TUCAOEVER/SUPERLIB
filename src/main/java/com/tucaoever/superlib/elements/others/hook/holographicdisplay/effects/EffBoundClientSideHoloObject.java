package com.tucaoever.superlib.elements.others.hook.holographicdisplay.effects;

import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.tucaoever.superlib.elements.others.hook.holographicdisplay.HoloManager;
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
import org.bukkit.entity.Entity;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Effect;

public class EffBoundClientSideHoloObject extends Effect
{
    private Expression<String> text;
    private Expression<String> id;
    private Expression<Entity> tar;
    private Expression<Player> player;
    private Expression<Number> xcord;
    private Expression<Number> ycord;
    private Expression<Number> zcord;
    
    public boolean init(final Expression<?>[] exp, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        this.text = (Expression<String>)exp[0];
        this.id = (Expression<String>)exp[1];
        this.tar = (Expression<Entity>)exp[2];
        this.player = (Expression<Player>)exp[3];
        this.xcord = (Expression<Number>)exp[4];
        this.ycord = (Expression<Number>)exp[5];
        this.zcord = (Expression<Number>)exp[6];
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
        final VisibilityManager visibilityManager = hologram.getVisibilityManager();
        visibilityManager.showTo((Player)this.player.getSingle(evt));
        visibilityManager.setVisibleByDefault(false);
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
                        stack = new ItemStack(mat, 1, (byte)meta);
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
        if (HoloManager.addToHoloMap(this.id.getSingle(evt).replace("\"", ""), hologram)) {
            hologram.delete();
        }
        else {
            final String tid = this.id.getSingle(evt).replace("\"", "");
            HoloManager.followEntity(this.tar.getSingle(evt), tid, hx, hy, hz);
        }
    }
}
