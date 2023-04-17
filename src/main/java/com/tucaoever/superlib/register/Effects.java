package com.tucaoever.superlib.register;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.PropertyExpression;
import com.tucaoever.superlib.elements.effects.*;
import com.tucaoever.superlib.elements.others.file.effects.EffCreateFile;
import com.tucaoever.superlib.elements.others.file.effects.EffDeleteFile;
import com.tucaoever.superlib.elements.others.gui.effects.EffCancelGUIClose;
import com.tucaoever.superlib.elements.others.gui.effects.EffOpenGUI;
import com.tucaoever.superlib.elements.others.json.EffJSONToVariable;
import com.tucaoever.superlib.elements.others.json.ExprJSONToString;
import com.tucaoever.superlib.elements.others.nbt.effects.EffSaveNBTFile;
import com.tucaoever.superlib.elements.others.nbt.effects.EffSetBlockNBT;
import com.tucaoever.superlib.elements.others.nbt.effects.EffSpawnEntityNBT;
import com.tucaoever.superlib.elements.others.scoreboard.effects.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Effects {
    public static void register() {
        PropertyExpression.register(ExprJSONToString.class, String.class,
                "[serialized] json (form|representation)",
                "objects");
        Skript.registerEffect(EffSendActionBar.class,
                "send [the] action bar [with text] %string% to %players%");
        Skript.registerEffect(EffCreateFile.class,
                "create file %string%",
                "create folder %string%");
        Skript.registerEffect(EffJSONToVariable.class,
                "(map|copy) [the] json [(of|from)] %string% to [the] [var[iable]] %objects%");
        Skript.registerEffect(EffEvaluateEffect.class,
                "evaluate %string%");
        Skript.registerEffect(EffDeleteFile.class,
                "delete (0¦(script|program|app[lication]|[zip ]file)|1¦dir[ectory]) %string%");
        Skript.registerEffect(EffCancelGUIClose.class,
                "(0¦cancel|1¦uncancel) [the] gui clos(e|ing)");
        Skript.registerEffect(EffOpenGUI.class,
                "(open|show) [[skript[-]]gui] %guiinventory% (to|for) %players%");
        Skript.registerEffect(EffLoadChunk.class,
                "(0¦load|1¦unload) chunk %chunk%");
        Skript.registerEffect(EffNameOfScore.class,
                "set name of sidebar (of|for) %players% to %string%");
        Skript.registerEffect(EffRemoveScoreboard.class, "(wipe|erase|delete) %player%['s] sidebar");
        Skript.registerEffect(EffCreateIdBasedScore.class,
                "(set|create) id [based] score %string% in sidebar of %player% to %number% with id %string%");
        Skript.registerEffect(EffEditIdBasedScore.class,
                "(edit|update) score [with] id %string% to %string% and %number%");
        Skript.registerEffect(EffDelIdBasedScore.class, "(delete|remove) score [with] id %string%");
        // Group ID Based scores
        Skript.registerEffect(EffCreateGroupIdScore.class,
                "(set|create) group id [based] score %string% in sidebar for %players% to %number% with id %string%");
        Skript.registerEffect(EffAddPlayerToGroupId.class,
                "add %player% to group score [with id] %string%");
        Skript.registerEffect(EffEditGroupIdScore.class,
                "(edit|update) score [with][in] group [id] %string% to %string% and %number%");
        Skript.registerEffect(EffRemovePlayerFromGroupId.class,
                "(delete|remove) %player% from group [id based] score %string%");
        Skript.registerEffect(EffDelGroupIdScore.class,
                "(delete|remove) score[s] [with] group id %string%");
        if (!Bukkit.getPluginManager().isPluginEnabled("SkBee")) {
            Skript.registerEffect(EffSaveNBTFile.class,
                    "save nbt file[s] (for|of) %nbtcompounds%");
            if (Skript.classExists("org.bukkit.block.data.BlockData") && Skript.classExists("ch.njol.skript.expressions.ExprBlockData")) {
                Skript.registerEffect(EffSetBlockNBT.class,
                        "set (nbt[(-| )]block|tile[(-| )]entity) %directions% %locations% to %itemtype/blockdata% with nbt %string/nbtcompound%");
            } else {
                Skript.registerEffect(EffSetBlockNBT.class,
                        "set (nbt[(-| )]block|tile[(-| )]entity) %directions% %locations% to %itemtype% with nbt %string/nbtcompound%");
            }
            Skript.registerEffect(EffSpawnEntityNBT.class,
                    "spawn %entitytypes% [%directions% %locations%] with nbt %string/nbtcompound%",
                    "spawn %number% of %entitytypes% [%directions% %locations%] with nbt %string/nbtcompound%");

            Skript.registerEffect(EffAddClickEvent.class,
                    "add click event with action %clickeventaction% (and|with|to) [(execute|text|link)] %string% to [text component] %textcomponent%");
            Skript.registerEffect(EffAddComponentToPage.class,
                    "add text component %textcomponents% to [book] %itemstack%");
            Skript.registerEffect(EffAddHoverEvent.class,
                    "add hover event with action %hovereventaction% (and|with) [(value|text)] %string% to [text component] %textcomponent%");
            Skript.registerEffect(EffMessageTextComponent.class,
                    "message text component %textcomponent% to %players%");
        }
    }
}
