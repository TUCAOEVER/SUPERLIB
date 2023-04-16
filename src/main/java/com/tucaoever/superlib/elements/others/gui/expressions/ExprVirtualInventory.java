package com.tucaoever.superlib.elements.others.gui.expressions;

import com.tucaoever.superlib.util.InventoryUtils;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Name("Virtual Inventory")
@Description("An expression to create inventories that can be used with GUIs.")
@Examples("create a gui with virtual chest inventory with 3 rows named \"My GUI\"")
@Since("1.0.0")
public class ExprVirtualInventory extends SimpleExpression<Inventory>{


	private Expression<InventoryType> inventoryType;
	private Expression<Number> size;
	private Expression<String> name;

	// The name of this inventory.
	private String invName;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
		inventoryType = (Expression<InventoryType>) exprs[0];
		if (matchedPattern > 1) {
			name = (Expression<String>) exprs[1];
			size = (Expression<Number>) exprs[2];
		} else {
			name = (Expression<String>) exprs[2];
			size = (Expression<Number>) exprs[1];
		}
		return true;
	}

	@Override
	protected Inventory[] get(Event e) {
		InventoryType type = inventoryType.getSingle(e);
		if (type == null)
			return new Inventory[]{};
		Number size = this.size != null ? this.size.getSingle(e) : null;
		String name = this.name != null ? this.name.getSingle(e) : null;
		invName = name != null ? name : type.getDefaultTitle();
		return new Inventory[]{InventoryUtils.newInventory(type, (size != null ? size.intValue() : 0), name)};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Inventory> getReturnType() {
		return Inventory.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "virtual " + inventoryType.toString(e, debug)
			+ (name != null ? " with name" + name.toString(e, debug) : "")
			+ (size != null ? " with " + size.toString(e, debug) + " rows" : "");
	}

	/**
	 * @return The name of this inventory.
	 */
	public String getName() {
		return invName;
	}

}
