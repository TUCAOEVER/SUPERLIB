package com.tucaoever.superlib.register;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.tucaoever.superlib.SUPERLIB;
import com.tucaoever.superlib.elements.others.hook.holographicdisplay.condtions.CondHologramExists;
import com.tucaoever.superlib.elements.others.hook.holographicdisplay.effects.*;
import com.tucaoever.superlib.elements.others.hook.holographicdisplay.expressions.ExprGetHoloLine;
import com.tucaoever.superlib.elements.others.hook.mmocore.expressions.ExprProfessionExp;
import com.tucaoever.superlib.elements.others.hook.mmoitems.conditions.CondIsItem;
import com.tucaoever.superlib.elements.others.hook.mmoitems.expressions.ExprId;
import com.tucaoever.superlib.elements.others.hook.mmoitems.expressions.ExprItem;
import com.tucaoever.superlib.elements.others.hook.mmoitems.expressions.ExprType;
import com.tucaoever.superlib.elements.others.hook.mythicmobs.conditions.CondIsActiveMob;
import com.tucaoever.superlib.elements.others.hook.mythicmobs.effects.EffSpawnMythicMob;
import com.tucaoever.superlib.elements.others.hook.mythicmobs.expressions.ExprActiveMob;
import com.tucaoever.superlib.elements.others.hook.mythicmobs.expressions.ExprEntity;
import com.tucaoever.superlib.elements.others.hook.mythicmobs.expressions.ExprMobType;
import com.tucaoever.superlib.elements.others.hook.placeholderapi.PlaceholderAPIEvent;
import com.tucaoever.superlib.elements.others.hook.placeholderapi.events.EvtPlaceholderRequest;
import com.tucaoever.superlib.elements.others.hook.placeholderapi.expressions.*;
import com.tucaoever.superlib.elements.others.hook.worldguard.effects.EffManageOwner;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.bukkit.events.MythicMobSpawnEvent;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Others {
    public static void register() {
        if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
            SUPERLIB.log("Enabled Support For WorldGuard");
            Skript.registerEffect(EffManageOwner.class,
                    "[worldguard] (0¦add|1¦remove) owner[s] %players% from region %string% in [world] %world%");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            SUPERLIB.log("Enabled Support For HolographicDisplays");
            Skript.registerEffect(EffCreateStaticHoloObject.class,
                    "create holo object %string% with id %string% at %location%");
            Skript.registerEffect(EffDeleteHoloObject.class,
                    "delete holo object %string%");
            Skript.registerEffect(EffEditHoloObjectLine.class,
                    "edit holo object %string% [with] line [number] %number% to %string% [and set interactivity to %-boolean%]");
            Skript.registerEffect(EffTimedHologram.class,
                    "create hologram %string% at %location% for %timespan%");
            Skript.registerEffect(EffCreateInteractiveStaticHolograms.class,
                    "create interactive holo object %string% with id %string% at %location%");
            Skript.registerEffect(EffDeleteHoloObject.class,
                    "delete holo object %string%");
            Skript.registerEffect(EffEditHoloObject.class,
                    "edit holo object %string% to %string% [and set interactivity to %-boolean%]");
            Skript.registerEffect(EffBoundHoloObject.class,
                    "create bound holo object %string% with id %string% to %entity% [offset by %number%, %number%( and|,) %number%]");
            Skript.registerEffect(EffDeleteHoloObjectLine.class,
                    "(delete|remove) line %number% in holo object %string%");
            Skript.registerCondition(CondHologramExists.class,
                    "(holo object|hologram) %string% exists");
            Skript.registerExpression(ExprGetHoloLine.class, String.class, ExpressionType.SIMPLE,
                    "text in line %number% of holo[gram] [object] %string%");
            Skript.registerEffect(EffCreateInteractiveStaticClientSideHolograms.class,
                    "create interactive client side holo object %string% with id %string% at %location% to %player%");
            Skript.registerEffect(EffTimedClientSideHolo.class,
                    "display hologram %string% at %location% to %player% for %timespan%");
            Skript.registerEffect(EffCreateStaticClientHoloObject.class,
                    "create client side holo object %string% with id %string% at %location% to %player%");
            Skript.registerEffect(EffBoundClientSideHoloObject.class,
                    "create client side bound holo object %string% with id %string% to %entity% for %player% [offset by %number%, %number%( and|,) %number%]");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            SUPERLIB.log("Enabled Support For PlaceholderAPI");
            Skript.registerExpression(ExprParse.class, String.class, ExpressionType.SIMPLE,
                    "[the] (placeholder[api]|papi) parse (string[s]|text) %strings% [(from|of) %offlineplayer%]");
            Skript.registerExpression(ExprPrefix.class, String.class, ExpressionType.SIMPLE,
                    "[the] [(placeholder[api]|papi)] (prefix|placeholder)");
            Skript.registerExpression(ExprIdentifier.class, String.class, ExpressionType.SIMPLE,
                    "[the] [(placeholder[api]|papi)] identifier");
            Skript.registerExpression(ExprPlaceholder.class, String.class, ExpressionType.SIMPLE,
                    "[the] ([value of] placeholder[s]|placeholder [value] [of]) %strings% [from %players%]");
            Skript.registerExpression(ExprResult.class, String.class, ExpressionType.SIMPLE,
                    "[the] [(placeholder[api]|papi)] result");
            Skript.registerEvent("Placeholder Request", EvtPlaceholderRequest.class, PlaceholderAPIEvent.class,
                    "(placeholder[api]|papi) request with [the] prefix %string%");
            EventValues.registerEventValue(PlaceholderAPIEvent.class, Player.class,
                    new Getter<>() {
                        public Player get(final PlaceholderAPIEvent e) {
                            return e.getPlayer();
                        }
                    }, 0);
            Skript.registerEvent("Placeholder Request", EvtPlaceholderRequest.class, PlaceholderAPIEvent.class,
                    "(placeholder[api]|papi) request with [the] prefix %string%");
            EventValues.registerEventValue(PlaceholderAPIEvent.class, Player.class,
                    new Getter<>() {
                        public Player get(final PlaceholderAPIEvent e) {
                            return e.getPlayer();
                        }
                    }, 0);
            Skript.registerEvent("Placeholder Request", EvtPlaceholderRequest.class, PlaceholderAPIEvent.class,
                    "(placeholderapi|papi) [placeholder] request (with|for) [the] prefix %string%");
            EventValues.registerEventValue(PlaceholderAPIEvent.class, Player.class,
                    new Getter<>() {
                        public Player get(PlaceholderAPIEvent e) {
                            return e.getPlayer();
                        }
                    }, 0);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("MMOItems")) {
            SUPERLIB.log("Enabled Support For MMOItems");
            Skript.registerExpression(ExprItem.class, ItemStack.class, ExpressionType.COMBINED,
                    "[the] mmoitem [with] type %string% [([and ]with)] id %string%");
            Skript.registerExpression(ExprId.class, String.class, ExpressionType.PROPERTY,
                    "[the] mmoitem id of %itemstack%");
            Skript.registerExpression(ExprType.class, String.class, ExpressionType.PROPERTY,
                    "[the] mmoitem type of %itemstack%");
            PropertyCondition.register(CondIsItem.class,
                    "mmoitem[s]", "itemstack");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("MythicMobs")) {
            SUPERLIB.log("Enabled Support For MythicMobs");
            Skript.registerExpression(ExprMobType.class, String.class, ExpressionType.PROPERTY,
                    "mobtype of %activemob%");
            Skript.registerExpression(ExprActiveMob.class, ActiveMob.class, ExpressionType.PROPERTY,
                    "activemob (of|from) %entity%");
            Skript.registerExpression(ExprEntity.class, Entity.class, ExpressionType.PROPERTY,
                    "entity (of|from) activemob %activemob%");
            Skript.registerEffect(EffSpawnMythicMob.class,
                    "(spawn|summon) (mythicmob[s]|activemob[s]) %string% [at] %location%",
                    "(spawn|summon) %number% of (mythicmob[s]|activemob[s]) %string% [at] %location%");
            PropertyCondition.register(CondIsActiveMob.class,
                    "activemob", "entity");

            Skript.registerEvent("MythicMobSpawnEvent", SimpleEvent.class, MythicMobSpawnEvent.class,
                    "mythicmob spawnevent");
            EventValues.registerEventValue(MythicMobSpawnEvent.class, ActiveMob.class, new Getter<>() {
                public ActiveMob get(MythicMobSpawnEvent e) {
                    return e.getMob();
                }
            }, 0);
            EventValues.registerEventValue(MythicMobSpawnEvent.class, Entity.class, new Getter<>() {
                public Entity get(MythicMobSpawnEvent e) {
                    return e.getEntity();
                }
            }, 0);

            Skript.registerEvent("MythicMobDeathEvent", SimpleEvent.class, MythicMobDeathEvent.class,
                    "mythicmob deathevent");
            EventValues.registerEventValue(MythicMobDeathEvent.class, ActiveMob.class, new Getter<>() {
                public ActiveMob get(MythicMobDeathEvent e) {
                    return e.getMob();
                }
            }, 0);
            EventValues.registerEventValue(MythicMobDeathEvent.class, Location.class, new Getter<>() {
                public Location get(MythicMobDeathEvent e) {
                    return e.getEntity().getLocation();
                }
            }, 0);
            EventValues.registerEventValue(MythicMobDeathEvent.class, Entity.class, new Getter<>() {
                public Entity get(MythicMobDeathEvent e) {
                    return e.getEntity();
                }
            }, 0);

            Classes.registerClass(new ClassInfo<>(ActiveMob.class, "activemob")
                    .name("activemob")
                    .user("activemob")
                    .defaultExpression(new EventValueExpression<>(ActiveMob.class))
                    .parser(new Parser<>() {
                        @Override
                        public boolean canParse(@NotNull ParseContext context) {
                            return false;
                        }

                        @Override
                        @NotNull
                        public String toString(ActiveMob activeMob, int flags) {
                            return activeMob.getMobType();
                        }

                        @Override
                        @NotNull
                        public String toVariableNameString(ActiveMob activeMob) {
                            return activeMob.getUniqueId().toString();
                        }
                    }));
        }
        if (Bukkit.getPluginManager().isPluginEnabled("MMOCore")) {
            SUPERLIB.log("Enabled Support For MMOCore");
            Skript.registerExpression(ExprProfessionExp.class, Number.class, ExpressionType.COMBINED,
                    "profession (exp[erience]) %string% of %player%");
        }
    }
}
