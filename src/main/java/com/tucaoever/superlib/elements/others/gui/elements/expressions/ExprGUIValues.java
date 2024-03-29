package com.tucaoever.superlib.elements.others.gui.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SectionSkriptEvent;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.tucaoever.superlib.SUPERLIB;
import com.tucaoever.superlib.elements.others.gui.elements.sections.SecCreateGUI;
import com.tucaoever.superlib.elements.others.gui.elements.sections.SecGUIOpenClose;
import com.tucaoever.superlib.elements.others.gui.elements.sections.SecMakeGUI;
import com.tucaoever.superlib.elements.others.gui.gui.GUI;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

@Name("GUI Values")
@Description("Different utility values for a GUI. Some are available in vanilla Skript. Not all values are available for the GUI close section.")
@Examples({
		"create a gui with virtual chest inventory:",
		"\tmake gui 10 with water bucket:",
		"\t\tset the gui item to lava bucket"
})
@Since("1.0.0")
public class ExprGUIValues extends SimpleExpression<Object> {

	private int pattern;
	private boolean isDelayed;
	// Whether the expression is being used in an open/close section
	private boolean openClose;

	private String toString = "gui values";

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		SkriptEvent skriptEvent = getParser().getCurrentSkriptEvent();
		if (!(matchedPattern == 12 && getParser().isCurrentSection(SecCreateGUI.class)) && !(skriptEvent instanceof SectionSkriptEvent
				&& ((SectionSkriptEvent) skriptEvent).isSection(SecMakeGUI.class, SecGUIOpenClose.class))) {
			Skript.error("You can't use '" + parseResult.expr + "' outside of a GUI make or open/close section.");
			return false;
		}

		openClose = skriptEvent instanceof SectionSkriptEvent && ((SectionSkriptEvent) skriptEvent).isSection(SecGUIOpenClose.class);

		pattern = matchedPattern;
		if (openClose && matchedPattern != 3 && matchedPattern != 9 && matchedPattern != 10 && matchedPattern != 12) {
			Skript.error("You can't use '" + parseResult.expr + "' in a GUI open/close section.");
			return false;
		}

		this.isDelayed = !isDelayed.isFalse(); // TRUE or UNKNOWN
		toString = parseResult.expr;

		return true;
	}

	@Override
	protected Object[] get(Event event) {
		if (pattern == 12) {
			GUI gui = SUPERLIB.getGUIManager().getGUI(event);
			return gui != null ? new GUI[]{gui} : new GUI[0];
		}

		if (openClose) {
			InventoryEvent e = (InventoryEvent) event;
			switch (pattern) {
				case 3:
					return new Inventory[]{e.getInventory()};
				case 9:
					// Ugly but oh well
					return new HumanEntity[]{(event instanceof InventoryCloseEvent ? ((InventoryCloseEvent) e).getPlayer() : ((InventoryOpenEvent) e).getPlayer())};
				case 10:
					return (e.getViewers().toArray(new HumanEntity[0]));
			}
		} else {
			InventoryClickEvent e = (InventoryClickEvent) event;
			switch (pattern) {
				case 0:
					return new Number[]{e.getSlot()};
				case 1:
					return new Number[]{e.getRawSlot()};
				case 2:
					return new Number[]{e.getHotbarButton()};
				case 3:
					Inventory clicked = e.getClickedInventory();
					return clicked != null ? new Inventory[]{clicked} : new Inventory[0];
				case 4:
					return new InventoryAction[]{e.getAction()};
				case 5:
					return new ClickType[]{e.getClick()};
				case 6:
					ItemStack cursor = e.getCursor();
					return cursor != null ? new ItemType[]{new ItemType(cursor)} : new ItemType[0];
				case 7:
					ItemStack currentItem = e.getCurrentItem();
					return currentItem != null ? new ItemType[]{new ItemType(currentItem)} : new ItemType[0];
				case 8:
					return new SlotType[]{e.getSlotType()};
				case 9:
					return new HumanEntity[]{e.getWhoClicked()};
				case 10:
					return e.getViewers().toArray(new HumanEntity[0]);
				case 11:
					GUI gui = SUPERLIB.getGUIManager().getGUI(event);
					return gui != null ? new String[]{"" + gui.convert(e.getSlot())} : new GUI[0];
			}
		}
		return new Object[0];
	}

	@Override
	@Nullable
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (isDelayed) {
			Skript.error("You can't set the '" + toString + "' when the event is already passed.");
			return null;
		}

		if (mode == ChangeMode.SET && pattern == 7) {
			return CollectionUtils.array(ItemType.class);
		}

		return null;
	}

	@Override
	public void change(Event event, Object [] delta, ChangeMode mode) {
		if (delta == null || !(event instanceof InventoryClickEvent)) {
			return;
		}
		((InventoryClickEvent) event).setCurrentItem(((ItemType) delta[0]).getRandom());
	}

	@Override
	public boolean isSingle() {
		return pattern != 10;
	}

	@Override
	public Class<?> getReturnType() {
		switch (pattern) {
			case 0:
			case 1:
			case 2:
				return Number.class;
			case 3:
				return Inventory.class;
			case 4:
				return InventoryAction.class;
			case 5:
				return ClickType.class;
			case 6:
			case 7:
				return ItemType.class;
			case 8:
				return SlotType.class;
			case 9:
			case 10:
				return HumanEntity.class;
			case 11:
				return String.class;
			case 12:
				return GUI.class;
			default:
				return Object.class;
		}
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return toString;
	}

}
