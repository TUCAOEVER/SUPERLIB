package com.tucaoever.superlib.elements.others.hook.mmocore.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.tucaoever.superlib.SUPERLIB;
import com.tucaoever.superlib.api.Description;
import com.tucaoever.superlib.api.Name;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import net.Indyuce.mmocore.experience.PlayerProfessions;
import net.Indyuce.mmocore.experience.Profession;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("MMOCore")
@Description("Change MMOCore profession expression.")
public class ExprProfessionExp extends SimpleExpression<Number> {

    private Expression<String> professionid;
    private Expression<Player> player;

    @Override
    protected Number[] get(Event e) {
        if (!MMOCore.plugin.professionManager.has(professionid.getSingle(e))) {
            SUPERLIB.error("Profession " + professionid.getSingle(e) + " does not exist");
            return null;
        }
        if (player.getSingle(e) == null) return null;
        Profession profession = MMOCore.plugin.professionManager.get(professionid.getSingle(e));
        double experience = PlayerData.get(player.getSingle(e)).getCollectionSkills().getExperience(profession);
        return new Number[]{experience};
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "player " + player + " profession " + professionid + "experience";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        professionid = (Expression<String>) exprs[0];
        player = (Expression<Player>) exprs[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        return (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.ADD) ? CollectionUtils.array(Number.class) : null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (!MMOCore.plugin.professionManager.has(professionid.getSingle(e))) {
            SUPERLIB.error("Profession " + professionid.getSingle(e) + " does not exist");
            return;
        }
        if (player.getSingle(e) == null) return;

        PlayerData playerData = PlayerData.get(player.getSingle(e));
        if (professionid.getSingle(e).equalsIgnoreCase("main")) {
            if (mode == Changer.ChangeMode.SET) {
                playerData.setExperience((double) delta[0]);
            } else if (mode == Changer.ChangeMode.ADD) {
                playerData.giveExperience((double) delta[0], EXPSource.OTHER);
            } else if (mode == Changer.ChangeMode.REMOVE) {
                playerData.giveExperience(-(double) delta[0], EXPSource.OTHER);
            }
        } else {
            Profession profession = MMOCore.plugin.professionManager.get(professionid.getSingle(e));
            if (mode == Changer.ChangeMode.SET) {
                PlayerProfessions playerProfessions = playerData.getCollectionSkills();
                playerProfessions.setExperience(profession, (int) delta[0]);
            } else if (mode == Changer.ChangeMode.ADD) {
                profession.giveExperience(PlayerData.get(player.getSingle(e)), (double) delta[0], null, EXPSource.OTHER);
            } else if (mode == Changer.ChangeMode.REMOVE) {
                profession.giveExperience(PlayerData.get(player.getSingle(e)), -(double) delta[0], null, EXPSource.OTHER);
            }
        }
    }
}
