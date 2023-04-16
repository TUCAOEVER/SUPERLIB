package com.tucaoever.superlib.elements.others.hook.holographicdisplay.effects;

import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.handler.PickupHandler;
import com.tucaoever.superlib.elements.others.hook.holographicdisplay.HoloManager;
import com.tucaoever.superlib.elements.others.hook.holographicdisplay.events.HoloPickupEvent;
import com.tucaoever.superlib.elements.others.hook.holographicdisplay.events.HoloTouchEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import ch.njol.skript.Skript;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Effect;

public class EffEditHoloObjectLine extends Effect
{
    private Expression<String> text;
    private Expression<String> id;
    private Expression<Boolean> interactive;
    private Expression<Number> lineNumber;
    
    public boolean init(final Expression<?>[] exp, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        this.id = (Expression<String>)exp[0];
        this.lineNumber = (Expression<Number>)exp[1];
        this.text = (Expression<String>)exp[2];
        this.interactive = (Expression<Boolean>)exp[3];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return null;
    }
    
    protected void execute(final Event evt) {
        if (HoloManager.getFromHoloMap(((String)this.id.getSingle(evt)).replace("\"", "")) != null) {
            final Hologram hologram = HoloManager.getFromHoloMap(((String)this.id.getSingle(evt)).replace("\"", ""));
            if (((Number)this.lineNumber.getSingle(evt)).intValue() <= 0) {
                Skript.error("Please use a line number greater than 0");
                return;
            }
            final int finalLineNumber = ((Number)this.lineNumber.getSingle(evt)).intValue() - 1;
            boolean check = false;
            if (this.interactive != null && this.interactive.getSingle(evt) != null) {
                check = (boolean)this.interactive.getSingle(evt);
            }
            hologram.getLine(finalLineNumber).removeLine();
            String line = ((String)this.text.getSingle(evt)).replace("\"", "").replace(";", "");
            if (line.startsWith("ItemStack:")) {
                line = line.substring(line.indexOf(":") + 1);
                int meta = 0;
                if (line.contains(":")) {
                    try {
                        meta = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                    }
                    catch (NumberFormatException exception) {
                        Skript.error("Meta data could not be parsed correctly!");
                        return;
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
                    return;
                }
                final ItemLine itemline = hologram.insertItemLine(finalLineNumber, stack);
                if (check) {
                    itemline.setTouchHandler((TouchHandler)new TouchHandler() {
                        public void onTouch(final Player player) {
                            final HoloTouchEvent event = new HoloTouchEvent(player, ((String)EffEditHoloObjectLine.this.id.getSingle(evt)).replace("\"", ""), finalLineNumber);
                            Bukkit.getPluginManager().callEvent((Event)event);
                        }
                    });
                    itemline.setPickupHandler((PickupHandler)new PickupHandler() {
                        public void onPickup(final Player player) {
                            final HoloPickupEvent event = new HoloPickupEvent(player, ((String)EffEditHoloObjectLine.this.id.getSingle(evt)).replace("\"", ""), finalLineNumber);
                            Bukkit.getPluginManager().callEvent((Event)event);
                        }
                    });
                }
            }
            else {
                final TextLine textline = hologram.insertTextLine(finalLineNumber, line);
                if (check) {
                    textline.setTouchHandler((TouchHandler)new TouchHandler() {
                        public void onTouch(final Player player) {
                            final HoloTouchEvent event = new HoloTouchEvent(player, ((String)EffEditHoloObjectLine.this.id.getSingle(evt)).replace("\"", ""), finalLineNumber);
                            Bukkit.getPluginManager().callEvent((Event)event);
                        }
                    });
                }
            }
            HoloManager.editHoloMap(((String)this.id.getSingle(evt)).replace("\"", ""), hologram);
        }
        else {
            Skript.error("That hologram does not exist!");
        }
    }
}
