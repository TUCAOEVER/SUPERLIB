package com.tucaoever.superlib.register;

import ch.njol.skript.Skript;
import com.tucaoever.superlib.elements.others.gui.elements.sections.SecCreateGUI;
import com.tucaoever.superlib.elements.others.gui.elements.sections.SecGUIOpenClose;
import com.tucaoever.superlib.elements.others.gui.elements.sections.SecMakeGUI;

public class Sections {

    public static void register() {
        Skript.registerSection(SecCreateGUI.class,
                "create [a] [new] gui [[with id[entifier]] %-string%] with %inventory% [removable:(and|with) ([re]move[e]able|stealable) items] [(and|with) shape %-strings%]",
                "(change|edit) [gui] %guiinventory%");
        Skript.registerSection(SecGUIOpenClose.class,
                "run (when|while) (open[ing]|1¦clos(e|ing)) [[the] gui]",
                "run (when|while) [the] gui (opens|1¦closes)",
                "run on gui (open[ing]|1¦clos(e|ing))");
        Skript.registerSection(SecMakeGUI.class,
                "(make|format) [the] next gui [slot] (with|to) [removable:([re]mov[e]able|stealable)] %itemtype%",
                "(make|format) gui [slot[s]] %strings/numbers% (with|to) [removable:([re]mov[e]able|stealable)] %itemtype%",
                "(un(make|format)|remove) [the] next gui [slot]",
                "(un(make|format)|remove) gui [slot[s]] %strings/numbers%",
                "(un(make|format)|remove) all [[of] the] gui [slots]");
    }
}
