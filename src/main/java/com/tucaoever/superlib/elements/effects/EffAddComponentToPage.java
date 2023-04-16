package com.tucaoever.superlib.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import javax.annotation.Nullable;

public class EffAddComponentToPage extends Effect {

    private Expression<TextComponent> component;
    private Expression<ItemStack> book;
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        component = (Expression<TextComponent>) e[0];
        book = (Expression<ItemStack>) e[1];
        return true;
    }
    @Override
    public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
        return "add text component %textcomponents% to [book] %itemstack%";
    }
    @SuppressWarnings("unchecked")
    @Override
    protected void execute(Event e) {
        final BookMeta bookMeta = (BookMeta) book.getSingle(e).getItemMeta();
        bookMeta.setTitle("");
        bookMeta.setAuthor("");
        bookMeta.spigot().addPage(component.getAll(e));
        book.getSingle(e).setItemMeta(bookMeta);
    }
}