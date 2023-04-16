package com.tucaoever.superlib.addons.skriptmirror.skript.custom;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.tucaoever.superlib.addons.skriptmirror.skript.custom.condition.ConditionCheckEvent;
import com.tucaoever.superlib.addons.skriptmirror.skript.custom.effect.EffectTriggerEvent;
import com.tucaoever.superlib.addons.skriptmirror.skript.custom.event.EventTriggerEvent;
import com.tucaoever.superlib.addons.skriptmirror.skript.custom.expression.ExpressionChangeEvent;
import com.tucaoever.superlib.addons.skriptmirror.skript.custom.expression.ExpressionGetEvent;
import org.bukkit.event.Event;

public class ExprParseMark extends SimpleExpression<Number> {
  static {
    Skript.registerExpression(ExprParseMark.class, Number.class, ExpressionType.SIMPLE,
        "[the] [parse[r]] mark");
  }

  @Override
  public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
    if (!getParser().isCurrentEvent(
      SyntaxParseEvent.class,
      ConditionCheckEvent.class,
      EffectTriggerEvent.class,
      EventTriggerEvent.class,
      ExpressionChangeEvent.class,
      ExpressionGetEvent.class
    )) {
      Skript.error("The parse mark may only be used in custom syntax");
      return false;
    }
    return true;
  }

  @Override
  protected Number[] get(Event e) {
    return new Number[]{((CustomSyntaxEvent) e).getParseResult().mark};
  }

  @Override
  public boolean isSingle() {
    return true;
  }

  @Override
  public Class<? extends Number> getReturnType() {
    return Number.class;
  }

  @Override
  public String toString(Event e, boolean debug) {
    return "parser mark";
  }
}
