package com.tucaoever.superlib.register;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.EnumSerializer;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import com.tucaoever.superlib.util.LambdaCondition;
import com.tucaoever.superlib.util.NBT.NBTCustomType;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.StreamCorruptedException;
import java.util.Map;

import static com.tucaoever.superlib.elements.others.nbt.types.SkriptTypes.NBT_COMPOUND_CHANGER;

public class Types {
    public static void register() {
        if (Classes.getExactClassInfo(ClickEvent.Action.class) == null) {
            Classes.registerClass(new ClassInfo<>(ClickEvent.Action.class, "clickeventaction")
                    .user("clickeventactions?")
                    .parser(new Parser<>() {
                        @Override
                        public ClickEvent.Action parse(@NotNull String s, @NotNull ParseContext parseContext) {
                            try {
                                return ClickEvent.Action.valueOf(ClickEvent.Action.class, s.replace(" ", "_").toUpperCase().trim());
                            } catch (IllegalArgumentException e) {
                                return null;
                            }
                        }

                        @Override
                        public @NotNull String toString(ClickEvent.Action e, int i) {
                            return e.toString();
                        }

                        @Override
                        public @NotNull String toVariableNameString(ClickEvent.Action e) {
                            return "clickeventaction:" + e.toString();
                        }
                    })
                    .serializer(new EnumSerializer<>(ClickEvent.Action.class)));
        }

        if (Classes.getExactClassInfo(HoverEvent.Action.class) == null) {
            Classes.registerClass(new ClassInfo<>(HoverEvent.Action.class, "hovereventaction")
                    .user("hovereventactions?")
                    .parser(new Parser<>() {
                        @Override
                        public HoverEvent.Action parse(@NotNull String s, @NotNull ParseContext parseContext) {
                            try {
                                return HoverEvent.Action.valueOf(HoverEvent.Action.class, s.replace(" ", "_").toUpperCase().trim());
                            } catch (IllegalArgumentException e) {
                                return null;
                            }
                        }

                        @Override
                        public @NotNull String toString(HoverEvent.Action e, int i) {
                            return e.toString();
                        }

                        @Override
                        public @NotNull String toVariableNameString(HoverEvent.Action e) {
                            return "hovereventaction:" + e.toString();
                        }
                    })
                    .serializer(new EnumSerializer<>(HoverEvent.Action.class)));
        }


        Classes.registerClass(new ClassInfo<>(LambdaCondition.class, "predicate")
                .parser(new Parser<>() {
                    @Override
                    public LambdaCondition parse(String s, ParseContext parseContext) {
                        if (s.length() > 2 && s.charAt(0) == '[' && s.charAt(s.length() - 1) == ']') {
                            Condition e = Condition.parse(s.substring(1, s.length() - 1), null);
                            if (e == null) {
                                Skript.error(s + " is not a valid lambda statement.", ErrorQuality.SEMANTIC_ERROR);
                            } else {
                                return new LambdaCondition(e);
                            }
                        }
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return true;
                    }

                    @Override
                    public String toString(LambdaCondition lambdaCondition, int i) {
                        return lambdaCondition.toString();
                    }

                    @Override
                    public String toVariableNameString(LambdaCondition lambdaCondition) {
                        return lambdaCondition.toString();
                    }
                }));
        Classes.registerClass(new ClassInfo<>(NBTCustomType.class, "nbttype")
                .user("nbt ?types?").name("NBT - Tag Type")
                .description("Represents a type of NBT tag.").usage(NBTCustomType.getNames())
                .examples("set byte tag \"points\" of {_nbt} to 1", "set compound tag \"tool\" of {_nbt} to nbt compound of player's tool")
                .since("1.10.0")
                .parser(new Parser<>() {

                    @Nullable
                    @Override
                    public NBTCustomType parse(String s, ParseContext context) {
                        return NBTCustomType.fromName(s);
                    }

                    @Override
                    public String toString(NBTCustomType nbtCustomType, int i) {
                        return nbtCustomType.getName();
                    }

                    @Override
                    public String toVariableNameString(NBTCustomType nbtCustomType) {
                        return toString(nbtCustomType, 0);
                    }

                }));
        Classes.registerClass(new ClassInfo<>(NBTCompound.class, "nbtcompound")
                .user("nbt ?compound").name("NBT - Compound")
                .description("Represents the NBT compound of an entity/block/item.")
                .usage("{id:\"minecraft:netherite_axe\",tag:{Damage:0,Enchantments:[{id:\"minecraft:unbreaking\",lvl:2s}]},Count:1b}")
                .examples("set {_a} to nbt compound of player")
                .since("1.6.0")
                .parser(new Parser<>() {

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(@NotNull NBTCompound nbt, int flags) {
                        return nbt.toString();
                    }

                    @Override
                    public String toVariableNameString(@NotNull NBTCompound nbt) {
                        return "nbt:" + nbt;
                    }
                })
                .serializer(new Serializer<>() {
                    @Override
                    public @NotNull Fields serialize(@NotNull NBTCompound nbt) {
                        Fields fields = new Fields();
                        fields.putObject("nbt", nbt.toString());
                        return fields;
                    }

                    @Override
                    public void deserialize(@NotNull NBTCompound o, @NotNull Fields f) {
                        assert false;
                    }

                    @Override
                    protected NBTCompound deserialize(@NotNull Fields fields) throws StreamCorruptedException {
                        String nbt = fields.getObject("nbt", String.class);
                        assert nbt != null;
                        try {
                            return new NBTContainer(nbt);
                        } catch (IllegalArgumentException ex) {
                            throw new StreamCorruptedException("Invalid nbt data: " + nbt);
                        }
                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                        return true;
                    }

                    @Override
                    protected boolean canBeInstantiated() {
                        return false;
                    }
                })
                .changer(NBT_COMPOUND_CHANGER));

        Classes.registerClass(new ClassInfo<>(TextComponent.class, "textcomponent")
                .name("textcomponent")
                .description("A getter for bungeecord's chat textcomponent.")
                .parser(new Parser<>() {
                    @Override
                    @Nullable
                    public TextComponent parse(String obj, ParseContext context) {
                        return null;
                    }

                    @Override
                    public String toString(TextComponent t, int flags) {
                        return t.toLegacyText();
                    }

                    @Override
                    public String toVariableNameString(TextComponent t) {
                        return t.toLegacyText();
                    }
                }));
        Classes.registerClass(new ClassInfo<>(BaseComponent.class, "basecomponent")
                .user("base ?components?")
                .name("Text Component - Base Component")
                .description("Text components used for hover/click events. Due to the complexity of these, ",
                        "they can NOT be long term stored in variables.")
                .examples("set {_t} to text component from \"CLICK FOR OUR DISCORD\"",
                        "set hover event of {_t} to a new hover event showing \"Clicky Clicky!\"",
                        "set click event of {_t} to a new click event to open url \"https://OurDiscord.com\"",
                        "send component {_t} to player")
                .since("1.5.0")
                .parser(new Parser<>() {
                    @Override
                    public @NotNull String toString(@NotNull BaseComponent o, int flags) {
                        return o.toLegacyText();
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toVariableNameString(@NotNull BaseComponent o) {
                        return o.toLegacyText();
                    }
                }));
    }

}