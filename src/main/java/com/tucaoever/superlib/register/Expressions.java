package com.tucaoever.superlib.register;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.util.Experience;
import ch.njol.skript.util.Timespan;
import com.tucaoever.superlib.elements.expressions.*;
import com.tucaoever.superlib.elements.others.file.expressions.ExprContent;
import com.tucaoever.superlib.elements.others.file.expressions.ExprFileName;
import com.tucaoever.superlib.elements.others.file.expressions.ExprFiles;
import com.tucaoever.superlib.elements.others.file.expressions.ExprURLText;
import com.tucaoever.superlib.elements.others.gui.elements.expressions.*;
import com.tucaoever.superlib.elements.others.gui.gui.GUI;
import com.tucaoever.superlib.elements.others.nbt.expressions.*;
import com.tucaoever.superlib.elements.others.scoreboard.expressions.*;
import com.tucaoever.superlib.util.NBT.NBTCustomType;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Expressions {

    public static void register() {
        Skript.registerExpression(ExprFishEventState.class, PlayerFishEvent.State.class, ExpressionType.SIMPLE,
                "fish[ing] [event] state");
        Skript.registerExpression(ExprFishEventEntity.class, Entity.class, ExpressionType.SIMPLE,
                "fish[ing] [event] caught entity",
                "fish[ing] [event] hook");
        Skript.registerExpression(ExprFishingExperience.class, Experience.class, ExpressionType.SIMPLE,
                "fish[ing] [event] experience");

        PropertyExpression.register(ExprFishHookHookedEntity.class, Entity.class,
                "hooked entity", "entities");
        PropertyExpression.register(ExprFishHookState.class, FishHook.HookState.class,
                "[fish] hook[ed] state", "entities");
        PropertyExpression.register(ExprFishHookWaitTime.class, Timespan.class,
                "(min|:max) wait time", "entities");
        PropertyExpression.register(ExprPufferFishState.class, Integer.class,
                "puff state", "entities");

        Skript.registerExpression(ExprItemWithLegacyLore.class, ItemType.class, ExpressionType.PROPERTY,
                "%itemtypes% with legacy lore %string%");
        Skript.registerExpression(ExprSubList.class, Object.class, ExpressionType.COMBINED,
                "sub[ ]list of %objects% from ind(ice[s]|ex[es]) %number% to %number%",
                "(first|1¦last) %number% elements of %objects%");
        Skript.registerExpression(ExprNewChunk.class, Chunk.class, ExpressionType.SIMPLE,
                "new chunk");
        Skript.registerExpression(ExprOldChunk.class, Chunk.class, ExpressionType.SIMPLE,
                "old chunk");
        Skript.registerExpression(ExprChunkEntities.class, Entity.class, ExpressionType.PROPERTY,
                "[the] entities (in|of) %chunks%",
                "%chunks%'[s] entities");
        Skript.registerExpression(ExprBlockCube.class, Block.class, ExpressionType.PROPERTY,
                "blocks within %location% (to|and) %location%");
        Skript.registerExpression(ExprChunkCoordinate.class, Number.class, ExpressionType.PROPERTY,
                "chunk( |-)(0¦x|1¦z)[( |-)coord] of %chunk%",
                "%chunk%'s chunk( |-)(0¦x|1¦z)[( |-)coord]");
        Skript.registerExpression(ExprChunkAtCoordinate.class, Chunk.class, ExpressionType.PROPERTY,
                "chunk at [coord[inate]s] %number%,[ ]%number% [(in|of) %world%]");
        Skript.registerExpression(ExprChunk.class, Chunk.class, ExpressionType.COMBINED,
                "chunk %number%, %number% [in %world%]",
                "chunks [from] %number%, %number% to %number%, %number% [in %world%]",
                "chunk at %location%",
                "chunks from %location% to %location%");
        Skript.registerExpression(ExprChunkBlock.class, Block.class, ExpressionType.PROPERTY,
                "block [at] %number%, %number%, %number% (of|in) %chunk%",
                "(0¦layer %-number%|1¦top|2¦bottom|3¦sea level) (0¦south|4¦north)(0¦east|8¦west) (0¦center|16¦corner) of %chunk%");
        Skript.registerExpression(ExprChunkBlocks.class, Block.class, ExpressionType.PROPERTY,
                "[all] blocks (of|in) %chunk%",
                "blocks [from] %number%, %number%, %number% to %number%, %number%, %number% (of|in) %chunk%",
                "(0¦layer %-number%|1¦top|2¦bottom|3¦sea level) (of|in) %chunk%",
                "[[blocks] from] (0¦layer %-number%|1¦top|2¦bottom|3¦sea level) to (0¦layer %-number%|4¦top|8¦bottom|12¦sea level) (of|in) %chunk%",
                "layers [from] %number% to %number% (of|in) %chunk%");
        Skript.registerExpression(ExprCalendar.class, Integer.class, ExpressionType.SIMPLE,
                "year",
                "month",
                "day",
                "hour",
                "minute[s]",
                "second[s]",
                "millisecond[s]");
        Skript.registerExpression(ExprURLText.class, String.class, ExpressionType.SIMPLE,
                "text from [url] %string%");
        Skript.registerExpression(ExprFileName.class, String.class, ExpressionType.PROPERTY,
                "(0¦name|1¦extension) of file %string%",
                "file %string%'s (0¦name|1¦extension)");
        Skript.registerExpression(ExprNewTextComponent.class, TextComponent.class, ExpressionType.COMBINED,
                "[a] [new] text component [with [text]] %string%");
        Skript.registerExpression(ExprFiles.class, String.class, ExpressionType.SIMPLE,
                "[all] file[s] in %string%");
        Skript.registerExpression(ExprContent.class, String.class, ExpressionType.SIMPLE,
                "content of file %string%");
        Skript.registerExpression(ExprNewBook.class, ItemStack.class, ExpressionType.SIMPLE,
                "[a] new [customized] book");

        PropertyExpression.register(ExprGUI.class, GUI.class,
                "gui",
                "players");
        PropertyExpression.register(ExprGUIProperties.class, Object.class,
                "(0¦[skript-gui] name[s]|1¦(size[s]|rows)|2¦shape[s]|3¦lock status[es])",
                "guiinventorys");
        PropertyExpression.register(ExprIDOfGUI.class, String.class,
                "id[entifier]",
                "guiinventorys");

        Skript.registerExpression(ExprGUIs.class, GUI.class, ExpressionType.SIMPLE,
                "all guis");
        Skript.registerExpression(ExprGUIIdentifiers.class, String.class, ExpressionType.SIMPLE,
                "[(all [[of] the]|the)] (global|registered) gui id(s|entifiers)");
        Skript.registerExpression(ExprGUIValues.class, Object.class, ExpressionType.SIMPLE,
                "[the] gui slot",
                "[the] gui raw slot",
                "[the] gui hotbar slot",
                "[the] gui inventory",
                "[the] gui inventory action",
                "[the] gui click (type|action)",
                "[the] gui cursor [item]",
                "[the] gui [(clicked|current)] item",
                "[the] gui slot type",
                "[the] gui player",
                "[the] gui (viewer|player)s",
                "[the] gui slot id",
                "[the] gui");
        Skript.registerExpression(ExprLastGUI.class, GUI.class, ExpressionType.SIMPLE,
                "[the] (last[ly] [(created|edited)]|(created|edited)) gui",
                "[the] gui [with [the] id[entifier]] %string%");
        Skript.registerExpression(ExprNextGUISlot.class, Character.class, ExpressionType.SIMPLE,
                "%guiinventorys%'[s] next gui slot[s]",
                "[the] next gui slot[s] of %guiinventorys%",
                "[the] next gui slot");
        Skript.registerExpression(ExprPaginatedList.class, Object.class, ExpressionType.SIMPLE,
                "page[s] %numbers% of %objects% with %number% lines");
        Skript.registerExpression(ExprVirtualInventory.class, Inventory.class, ExpressionType.SIMPLE,
                "virtual (1¦(crafting [table]|workbench)|2¦chest|3¦anvil|4¦hopper|5¦dropper|6¦dispenser|%-inventorytype%) [with size %-number%] [(named|with (name|title)) %-string%]",
                "virtual (1¦(crafting [table]|workbench)|2¦chest|3¦anvil|4¦hopper|5¦dropper|6¦dispenser|%-inventorytype%) [with %-number% row[s]] [(named|with (name|title)) %-string%]",
                "virtual (1¦(crafting [table]|workbench)|2¦chest|3¦anvil|4¦hopper|5¦dropper|6¦dispenser|%-inventorytype%) [(named|with (name|title)) %-string%] with size %-number%",
                "virtual (1¦(crafting [table]|workbench)|2¦chest|3¦anvil|4¦hopper|5¦dropper|6¦dispenser|%-inventorytype%) [(named|with (name|title)) %-string%] with %-number% row[s]");
        //Todo GUI 注册
        Skript.registerExpression(ExprSortWithCustomOutput.class, String.class, ExpressionType.SIMPLE,
                "sorted %numbers% from highest to lowest with (output|format) %string%",
                "sorted %numbers% from lowest to highest with (output|format) %string%");
        Skript.registerExpression(ExprCheck.class, Boolean.class, ExpressionType.PROPERTY,
                "check[ed] %predicate%");
        Skript.registerExpression(ExprTabCompletion.class, String.class, ExpressionType.SIMPLE,
                "tab completions [(of|for) position %number%]");
        Skript.registerExpression(ExprTabCompletionArgs.class, String.class, ExpressionType.SIMPLE,
                "tab [complete] arg[ument](0¦s|1¦[(-| )]%number%)");
        Skript.registerExpression(ExprScoreNameFromId.class, String.class, ExpressionType.SIMPLE,
                "score (name|title) (of|from) id %string%");
        Skript.registerExpression(ExprScoreNameFromGroupId.class, String.class, ExpressionType.SIMPLE,
                "group score (name|title) (of|from) id %string%");
        Skript.registerExpression(ExprScoreValueFromId.class, Number.class, ExpressionType.SIMPLE,
                "score (value|number) (of|from) id %string%");
        Skript.registerExpression(ExprScoreValueFromGroupId.class, Number.class, ExpressionType.SIMPLE,
                "group score (value|number) (of|from) id %string%");
        Skript.registerExpression(ExprScoreBoardTitle.class, String.class, ExpressionType.SIMPLE,
                "sidebar (title|name) for %player%");
        Skript.registerExpression(ExprBookPages.class, BaseComponent.class, ExpressionType.PROPERTY,
                "page %number% of [book] %itemtype%");
        Skript.registerExpression(ExprClickEvent.class, ClickEvent.class, ExpressionType.COMBINED,
                "[a] [new] click event to run command %string%",
                "[a] [new] click event to suggest command %string%",
                "[a] [new] click event to open (link|url) %string%",
                "[a] [new] click event to copy %string% to clipboard",
                "[a] [new] click event to change to page %number%");
        Skript.registerExpression(ExprHoverEvent.class, HoverEvent.class, ExpressionType.COMBINED,
                "[a] [new] hover event showing %strings%");
        PropertyExpression.register(ExprClickEventOf.class, ClickEvent.class,
                "click event",
                "basecomponents");
        PropertyExpression.register(ExprHoverEventOf.class, HoverEvent.class,
                "hover event",
                "basecomponents");
        Skript.registerExpression(ExprItemFromNBT.class, ItemType.class, ExpressionType.PROPERTY,
                "item[s] (from|of) nbt[s] %strings/nbtcompounds%",
                "nbt item[s] (from|of) %strings/nbtcompounds%");
        Skript.registerExpression(ExprNBTUuid.class, Object.class, ExpressionType.SIMPLE,
                "uuid (int array[(1¦ as string)]|2¦most[ bits]|3¦least[ bits]) [(from|of) %-offlineplayer/entity%]");
        Skript.registerExpression(ExprNoClip.class, Boolean.class, ExpressionType.PROPERTY,
                "no[( |-)]clip (state|mode) of %entities%",
                "%entities%'s no[( |-)]clip (state|mode)");
        Skript.registerExpression(ExprObjectNBT.class, Object.class, ExpressionType.PROPERTY,
                "block/entity/itemstack/itemtype/slot/string");
        Skript.registerExpression(ExprPrettyNBT.class, String.class, ExpressionType.PROPERTY,
                "pretty nbt (of|from) %nbtcompounds/strings% [(with|using) split %-string%]",
                "%nbtcompounds/strings%'[s] pretty nbt [(with|using) split %-string%]");
        Skript.registerExpression(ExprTagOfNBT.class, Object.class, ExpressionType.COMBINED,
                "tag %string% of %string/nbtcompound%",
                "%string% tag of %string/nbtcompound%",
                "%nbttype% %string% of %nbtcompound%",
                "%string% %nbttype% of %nbtcompound%");
        PropertyExpression.register(ExprTagsOfNBT.class, String.class,
                "nbt tags",
                "nbtcompound");
        Skript.registerExpression(ExprTagTypeOfNBT.class, NBTCustomType.class, ExpressionType.COMBINED,
                "[nbt[ ]]tag[ ]type of tag %string% of %nbtcompound%");
        Skript.registerExpression(ExprItemWithNBT.class, ItemType.class, ExpressionType.PROPERTY,
                "%itemtype% with [[item( |-)]nbt] %nbtcompound%");
        Skript.registerExpression(ExprNbtCompound.class, NBTCompound.class, ExpressionType.PROPERTY,
                "nbt compound [(1¦copy)] (of|from) %blocks/entities/itemtypes/itemstacks/slots/strings%",
                "nbt compound [(1¦copy)] (of|from) file[s] %strings%");
    }
}