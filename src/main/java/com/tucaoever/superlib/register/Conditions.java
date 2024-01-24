package com.tucaoever.superlib.register;

import ch.njol.skript.Skript;
import ch.njol.skript.conditions.base.PropertyCondition;
import com.tucaoever.superlib.elements.conditions.CondFishHookInOpenWater;
import com.tucaoever.superlib.elements.conditions.CondOperations;
import com.tucaoever.superlib.elements.others.file.conditions.CondFileExists;
import com.tucaoever.superlib.elements.others.gui.elements.conditions.CondHasGUI;
import com.tucaoever.superlib.elements.others.scoreboard.conditions.CondIsScoreboardSet;

public class Conditions {
    public static void register() {
        Skript.registerCondition(CondFileExists.class,
                "(file|folder) %string% exist[s]",
                "(file|folder) %string% (does not|doesn't|is not|isn't) exist[s]");
        Skript.registerCondition(CondIsScoreboardSet.class,
                "side bar is set for %player%");
        Skript.registerCondition(CondOperations.class,
                "<.+> && <.+>",
                "<.+> \\|\\| <.+>");
        Skript.registerCondition(CondHasGUI.class,
                "%players% (has|have) a gui [open]",
                "%players% (doesn't|does not|do not|don't) have a gui [open]");
        PropertyCondition.register(CondFishHookInOpenWater.class,
                "in open water", "entities");

    }
}

