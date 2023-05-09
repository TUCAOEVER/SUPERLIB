package com.tucaoever.superlib.elements.others.hook.holographicdisplay.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.tucaoever.superlib.SUPERLIB;
import com.tucaoever.superlib.elements.others.hook.holographicdisplay.HoloManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;

public class EffCreateStaticClientHoloObject extends Effect
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
        if (HoloManager.addToHoloMap(((String) this.id.getSingle(evt)).replace("\"", ""), hologram)) {
            hologram.delete();
        }
    }
}
