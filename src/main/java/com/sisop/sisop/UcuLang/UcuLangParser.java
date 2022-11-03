package com.sisop.sisop.UcuLang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */
public class UcuLangParser {
    public enum TokenType {
        Label,
        LocalLabel,
        Jump,
        Call,
        LocalJump,
        LocalCall,
        Number,
        StrLiteral,
        EmptyArray,
        Comment,
        VariableDefinition,
        VariablePush,
        Command,
        LocalVariableDefinition,
        LocalVariablePush,
    }

    public class Token {
        public final TokenType type;
        public final String token;
        public final UcuValue value;

        public Token(TokenType type, String token, UcuValue value) {
            this.type = type;
            this.token = token;
            this.value = value;
        }

        public Token(TokenType type, String token) {
            this.type = type;
            this.token = token;
            this.value = null;
        }
    }

    private static final Pattern space = Pattern.compile("\\G\\s+");
    private static final Pattern label = Pattern.compile("\\G:([^\\s]+)");
    private static final Pattern localLabel = Pattern.compile("\\G::([^\\s]+)");
    private static final Pattern jump = Pattern.compile("\\G@([^\\s]+)");
    private static final Pattern call = Pattern.compile("\\G\\(([^\\s]+)\\)");
    private static final Pattern localJump = Pattern.compile("\\G@:([^\\s]+)");
    private static final Pattern localCall = Pattern.compile("\\G\\(:([^\\s]+)\\)");
    private static final Pattern varDef = Pattern.compile("\\G\\.([^\\s]+)");
    private static final Pattern varPush = Pattern.compile("\\G\\$([^\\s]+)");
    private static final Pattern localVarDef = Pattern.compile("\\G\\.\\.([^\\s]+)");
    private static final Pattern localVarPush = Pattern.compile("\\G\\$\\$([^\\s]+)");
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
            String value = matcher.group(1);
            return new Token(TokenType.StrLiteral, value, new UcuValue(new UcuString(value)));
        }

        matcher.usePattern(emptyArray);
        if (matcher.find()) {
            return new Token(TokenType.EmptyArray, matcher.group(), new UcuValue(new UcuList()));
        }

        matcher.usePattern(localLabel);
        if (matcher.find()) {
            return new Token(TokenType.LocalLabel, matcher.group(1));
        }

        matcher.usePattern(label);
        if (matcher.find()) {
            return new Token(TokenType.Label, matcher.group(1));
        }

        matcher.usePattern(localJump);
        if (matcher.find()) {
            return new Token(TokenType.LocalJump, matcher.group(1));
        }

        matcher.usePattern(jump);
        if (matcher.find()) {
            return new Token(TokenType.Jump, matcher.group(1));
        }

        matcher.usePattern(localCall);
        if (matcher.find()) {
            return new Token(TokenType.LocalCall, matcher.group(1));
        }

        matcher.usePattern(call);
        if (matcher.find()) {
            return new Token(TokenType.Call, matcher.group(1));
        }

        matcher.usePattern(comment);
        if (matcher.find()) {
            return new Token(TokenType.Comment, matcher.group());
        }

        matcher.usePattern(localVarDef);
        if (matcher.find()) {
            return new Token(TokenType.LocalVariableDefinition, matcher.group(1));
        }

        matcher.usePattern(localVarPush);
        if (matcher.find()) {
            return new Token(TokenType.LocalVariablePush, matcher.group(1));
        }

        matcher.usePattern(varDef);
        if (matcher.find()) {
            return new Token(TokenType.VariableDefinition, matcher.group(1));
        }

        matcher.usePattern(varPush);
        if (matcher.find()) {
            return new Token(TokenType.VariablePush, matcher.group(1));
        }

        matcher.usePattern(anything);
        if (matcher.find()) {
            Double number = tryParseNumber(matcher.group());
            if (number != null) {
                return new Token(TokenType.Number, matcher.group(), new UcuValue(new UcuNumber(number)));
            } else {
                return new Token(TokenType.Command, matcher.group());
            }
        }

        return null;
    }

    private Double tryParseNumber(String value) {
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
