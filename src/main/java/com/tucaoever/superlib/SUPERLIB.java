package com.tucaoever.superlib;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.tucaoever.superlib.addons.skriptmirror.LibraryLoader;
import com.tucaoever.superlib.addons.skriptmirror.ParseOrderWorkarounds;
import com.tucaoever.superlib.addons.skriptyaml.utils.versions.SkriptAdapter;
import com.tucaoever.superlib.addons.skriptyaml.utils.versions.V2_6;
import com.tucaoever.superlib.addons.skriptyaml.utils.yaml.SkriptYamlConstructor;
import com.tucaoever.superlib.addons.skriptyaml.utils.yaml.SkriptYamlRepresenter;
import com.tucaoever.superlib.elements.others.gui.SkriptClasses;
import com.tucaoever.superlib.elements.others.gui.SkriptConverters;
import com.tucaoever.superlib.elements.others.gui.gui.GUIManager;
import com.tucaoever.superlib.elements.others.gui.gui.events.GUIEvents;
import com.tucaoever.superlib.elements.others.gui.gui.events.RecipeEvent;
import com.tucaoever.superlib.elements.others.scoreboard.ScoreBoardManager;
import com.tucaoever.superlib.register.*;
import com.tucaoever.superlib.util.NBT.NBTApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import static com.tucaoever.superlib.addons.skriptmirror.util.SkriptReflection.disableAndOrWarnings;

public class SUPERLIB extends JavaPlugin {

    public static ScoreBoardManager scoreboardManager;
    private static NBTApi nbtApi;
    private static SUPERLIB instance;
    private static SkriptYamlRepresenter representer;
    private static SkriptYamlConstructor constructor;
    private static GUIManager manager;
    private static RowSetFactory rowSetFactory;
    private final SkriptAdapter adapter = new V2_6();

    public static SUPERLIB getInstance() {
        return instance;
    }


    public static String getDefaultPath(final String pth) {
        final String dp = Paths.get("").normalize().toAbsolutePath().toString();
        if (pth.contains(dp)) {
            return pth + File.separator;
        }
        return dp + File.separator + pth;
    }

    public static void log(final String log) {
        Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&8[&6SUPERLIB&8] &7" + log));
    }

    public static void error(final String error) {
        Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&8[&cError&8] &c" + error));
    }

    public static void warn(final String error) {
        Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&8[&eWarn&8] &e" + error));
    }

    public static ScoreBoardManager getBoardManager() {
        return scoreboardManager;
    }

    public static RowSetFactory getRowSetFactory() {
        return rowSetFactory;
    }

    public static GUIManager getGUIManager() {
        return manager;
    }

    public void onEnable() {
        saveDefaultConfig();

        SUPERLIB.instance = this;
        SkriptAddon addon = Skript.registerAddon(this).setLanguageFileDirectory("lang");

        Conditions.register();
        Effects.register();
        Events.register();
        Expressions.register();
        Types.register();
        Sections.register();
        Others.register();

        new SkriptClasses();
        new SkriptConverters();
        manager = new GUIManager();
        getServer().getPluginManager().registerEvents(new GUIEvents(), this);
        if (Skript.classExists("com.destroystokyo.paper.event.player.PlayerRecipeBookClickEvent")) {
            // We need to track this event (see https://github.com/APickledWalrus/skript-gui/issues/33)
            getServer().getPluginManager().registerEvents(new RecipeEvent(), this);
        }

        try {
            rowSetFactory = RowSetProvider.newFactory();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        try {
            addon.loadClasses("com.tucaoever.superlib.addons.skriptyaml.skript");
            addon.loadClasses("com.tucaoever.superlib.addons.skriptdb.skript");
            addon.loadClasses("com.tucaoever.superlib.addons.skriptmirror.skript");
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        try {
            Path dataFolder = SUPERLIB.getInstance().getDataFolder().toPath();
            LibraryLoader.loadLibraries(dataFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ParseOrderWorkarounds.reorderSyntax();

        // Disable *all* and/or warnings
        disableAndOrWarnings();

        nbtApi = new NBTApi();
        scoreboardManager = new ScoreBoardManager(this);
        representer = new SkriptYamlRepresenter();
        constructor = new SkriptYamlConstructor();

        SUPERLIB.log("SUBPERLIB-" + getDescription().getVersion() + " is enabled! Designed by TUCAOEVER.");
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    public NBTApi getNbtApi() {
        return nbtApi;
    }

    public SkriptYamlRepresenter getRepresenter() {
        return representer;
    }

    public SkriptYamlConstructor getConstructor() {
        return constructor;
    }

    public SkriptAdapter getSkriptAdapter() {
        return adapter;
    }
}


