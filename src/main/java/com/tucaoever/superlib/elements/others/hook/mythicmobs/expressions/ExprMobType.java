package com.tucaoever.superlib.elements.others.hook.mythicmobs.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.api.Description;
import com.tucaoever.superlib.api.Examples;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Description("get internal name of active mob")
@Examples("set {_mobtype} to mobtype of event-entity")
public class ExprMobType extends SimpleExpression<String> {
    private Expression<ActiveMob> activeMobSingle;

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    protected @Nullable String[] get(Event e) {
        return new String[]{
                activeMobSingle.getSingle(e).getMobType()};
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        activeMobSingle = (Expression<ActiveMob>) exprs[0];
        return true;
    }
}
