package com.tucaoever.superlib.register;

import ch.njol.skript.Skript;
import com.tucaoever.superlib.elements.conditions.CondOperations;
import com.tucaoever.superlib.elements.others.file.conditions.CondFileExists;
import com.tucaoever.superlib.elements.others.gui.conditions.CondHasGUI;
import com.tucaoever.superlib.elements.others.gui.sections.SecCreateGUI;
import com.tucaoever.superlib.elements.others.gui.sections.SecMakeGUI;
import com.tucaoever.superlib.elements.others.gui.sections.SecOnCloseGUI;
import com.tucaoever.superlib.elements.others.scoreboard.conditions.CondIsScoreboardSet;

public class Conditions {
    public static void register() {
        Skript.registerCondition(SecMakeGUI.class,
                "(make|format) [the] next gui [slot] (with|to) [(1¦([re]mov[e]able|stealable))] %itemtype%",
                "(make|format) gui [slot[s]] %strings/numbers% (with|to) [(1¦([re]mov[e]able|stealable))] %itemtype%",
                "(un(make|format)|remove) [the] next gui [slot]",
                "(un(make|format)|remove) gui [slot[s]] %strings/numbers%",
                "(un(make|format)|remove) all [of the] gui [slots]");
        Skript.registerCondition(SecCreateGUI.class,
                "create [a] [new] gui [[with id[entifier]] %-string%] with %inventory% [(1¦(and|with) (moveable|stealable) items)] [(and|with) shape %-strings%]",
                "(change|edit) [gui] %guiinventory%");
        Skript.registerCondition(SecOnCloseGUI.class,
                "run (when|while) clos(e|ing) [[the] gui]",
                "run (when|while) [the] gui closes",
                "run on gui clos(e|ing)");
        Skript.registerCondition(CondHasGUI.class,
                "%players% (has|have) a gui [open]",
                "%players% (doesn't|does not|do not|don't) have a gui [open]");
        Skript.registerCondition(CondFileExists.class,
                "(file|folder) %string% exist[s]",
                "(file|folder) %string% (does not|doesn't|is not|isn't) exist[s]");
        Skript.registerCondition(CondIsScoreboardSet.class,
                "side bar is set for %player%");
        Skript.registerCondition(CondOperations.class,
                "<.+> && <.+>",
                "<.+> \\|\\| <.+>");
    }
}

