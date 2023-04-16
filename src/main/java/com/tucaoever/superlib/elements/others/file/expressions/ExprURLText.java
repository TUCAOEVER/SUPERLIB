package com.tucaoever.superlib.elements.others.file.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class ExprURLText extends SimplePropertyExpression<String, String> {

    @NotNull
    protected String getPropertyName() {
        return "URL";
    }

    public String convert(String s) {
        String string;
        try {
            final URL url = new URL(s);
            final Scanner a = new Scanner(url.openStream());
            StringBuilder str = new StringBuilder();
            boolean first = true;
            while (a.hasNext()) {
                if (first) {
                    str = new StringBuilder(a.next());
                } else {
                    str.append(" ").append(a.next());
                }
                first = false;
            }
            string = str.toString();
        } catch (IOException ignored) {
            string = null;
        }
        return string;
    }

    @NotNull
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
