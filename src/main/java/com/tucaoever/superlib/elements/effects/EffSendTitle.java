package com.tucaoever.superlib.elements.effects;

import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;

public class EffSendTitle extends Effect {

        private final static boolean TIME_SUPPORTED = Skript.methodExists(Player.class,"sendTitle", String.class, String.class, int.class, int.class, int.class);

        @Nullable
        private Expression<String> title;
        @Nullable
        private Expression<String> subtitle;
        @SuppressWarnings("null")
        private Expression<Player> recipients;
        @Nullable
        private Expression<Timespan> fadeIn, stay, fadeOut;

        @SuppressWarnings({"unchecked", "null"})
        @Override
        public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final ParseResult parser) {
            title = matchedPattern == 0 ? (Expression<String>) exprs[0] : null;
            subtitle = (Expression<String>) exprs[1 - matchedPattern];
            recipients = (Expression<Player>) exprs[2 - matchedPattern];
            if (TIME_SUPPORTED) {
                stay = (Expression<Timespan>) exprs[3 - matchedPattern];
                fadeIn = (Expression<Timespan>) exprs[4 - matchedPattern];
                fadeOut = (Expression<Timespan>) exprs[5 - matchedPattern];
            }
            return true;
        }

        @SuppressWarnings("null")
        @Override
        protected void execute(final Event e) {
            String title = this.title != null ? this.title.getSingle(e) : "",
                    sub = subtitle != null ? subtitle.getSingle(e) : null;

            if (TIME_SUPPORTED) {
                int in = fadeIn != null ? (int) fadeIn.getSingle(e).getTicks_i() : -1,
                        stay = this.stay != null ? (int) this.stay.getSingle(e).getTicks_i() : -1,
                        out = fadeOut != null ? (int) fadeOut.getSingle(e).getTicks_i() : -1;

                for (Player p : recipients.getArray(e))
                    p.sendTitle(title, sub, in, stay, out);
            } else {
                for (Player p : recipients.getArray(e))
                    p.sendTitle(title, sub);
            }
        }

        // TODO: util method to simplify this
        @Override
        public String toString(final @Nullable Event e, final boolean debug) {
            String title = this.title != null ? this.title.toString(e, debug) : "",
                    sub = subtitle != null ? subtitle.toString(e, debug) : "",
                    in = fadeIn != null ? fadeIn.toString(e, debug) : "",
                    stay = this.stay != null ? this.stay.toString(e, debug) : "",
                    out = fadeOut != null ? this.fadeOut.toString(e, debug) : "";
            return ("send title " + title +
                    sub == "" ? "" : " with subtitle " + sub) + " to " +
                    recipients.toString(e, debug) + (TIME_SUPPORTED ?
                    " for " + stay + " with fade in " + in + " and fade out" + out : "");
        }

    }
