package com.sisop.sisop.UcuLang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class UcuLangParser {
    public enum TokenType {
        Label,
        Jump,
        Call,
        Number,
        StrLiteral,
        EmptyArray,
        Comment,
        VariableDefinition,
        VariablePush,
        Command,
    }

    public class Token {
        public TokenType type;
        public String token;

        public Token(TokenType type, String token) {
            this.type = type;
            this.token = token;
        }
    }

    private static final Pattern space = Pattern.compile("\\G\\s+");
    private static final Pattern label = Pattern.compile("\\G:([^\\s]+)");
    private static final Pattern jump = Pattern.compile("\\G@([^\\s]+)");
    private static final Pattern call = Pattern.compile("\\G\\(([^\\s]+)\\)");
    private static final Pattern varDef = Pattern.compile("\\G\\.([^\\s]+)");
    private static final Pattern varPush = Pattern.compile("\\G\\$([^\\s]+)");
    // private static final Pattern number = Pattern.compile("\\G[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
    // private static final Pattern number = Pattern.compile("\\G[-]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
    private static final Pattern strLiteral = Pattern.compile("\\G\"([^\"]*)\"");
    private static final Pattern emptyArray = Pattern.compile("\\G\\[\\s*]\\s*");
    private static final Pattern comment = Pattern.compile("\\G\\{[^\\}]*\\}");
    private static final Pattern anything = Pattern.compile("\\G[^\\s]+");

    private final String code;

    private Matcher matcher;

    public UcuLangParser(String sourceCode) {
        code = sourceCode;
        matcher = space.matcher(code);
    }
    
    public Token next() {
        // Saltea espacios
        matcher.usePattern(space);
        matcher.find();

        matcher.usePattern(strLiteral);
        if (matcher.find()) {
            return new Token(TokenType.StrLiteral, matcher.group(1));
        }

        matcher.usePattern(emptyArray);
        if (matcher.find()) {
            return new Token(TokenType.EmptyArray, matcher.group());
        }

        matcher.usePattern(label);
        if (matcher.find()) {
            return new Token(TokenType.Label, matcher.group(1));
        }

        matcher.usePattern(jump);
        if (matcher.find()) {
            return new Token(TokenType.Jump, matcher.group(1));
        }

        matcher.usePattern(call);
        if (matcher.find()) {
            return new Token(TokenType.Call, matcher.group(1));
        }

        matcher.usePattern(comment);
        if (matcher.find()) {
            return new Token(TokenType.Comment, matcher.group());
        }

        matcher.usePattern(varDef);
        if (matcher.find()) {
            return new Token(TokenType.VariableDefinition, matcher.group(1));
        }

        matcher.usePattern(varPush);
        if (matcher.find()) {
            return new Token(TokenType.VariablePush, matcher.group(1));
        }

        // matcher.usePattern(number);
        // if (matcher.find()) {
        //     return new Token(TokenType.Number, matcher.group());
        // }

        matcher.usePattern(anything);
        if (matcher.find()) {
            if (isNumber(matcher.group())) {
                return new Token(TokenType.Number, matcher.group());
            } else {
                return new Token(TokenType.Command, matcher.group());
            }
        }

        return null;
    }

    private boolean isNumber(String value) {
        try {
            Double.valueOf(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
