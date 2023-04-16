package com.tucaoever.superlib.elements.others.gui.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.tucaoever.superlib.SUPERLIB;
import com.tucaoever.superlib.elements.others.gui.GUI;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

@Name("GUI of Player")
@Description("The GUI that the player currently has open.")
@Examples({"edit the player's gui:",
		"\tmake gui 1 with dirt named \"Edited Slot\""})
@Since("1.1.0")
public class ExprGUI extends SimplePropertyExpression<Player, GUI> {

	@Nullable
	@Override
	public GUI convert(Player player) {
		return SUPERLIB.getGUIManager().getGUI(player);
	}

	@Override
	public Class<? extends GUI> getReturnType() {
		return GUI.class;
	}

	@Override
	protected String getPropertyName() {
		return "gui";
	}

}
