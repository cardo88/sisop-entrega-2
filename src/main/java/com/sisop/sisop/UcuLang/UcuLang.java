package com.sisop.sisop.UcuLang;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 */
public class UcuLang {
    private UcuInstruction[] instructions = new UcuInstruction[0];
    private UcuContext context = new UcuContext();
    private String sourceCode; 

    public void compile(String src) {
        compile(src, new UcuCommand[0]);
    }

    public void compile(String src, UcuCommand[] extraCommands) {
        sourceCode = src;

        LinkedList<UcuInstruction> compiledInstructions = new LinkedList<>();

        var instructionMap = commandsToMap(new UcuCommand[] {
            new UcuAdd(),
            new UcuSub(),
            new UcuMul(),
            new UcuDiv(),
            new UcuGraterThan(),
            new UcuGreaterThanOrEqual(),
            new UcuLessThan(),
            new UcuLessThanOrEqual(),
            new UcuEquals(),
            new UcuDuplicate(),
            new UcuSwap(),
            new UcuOver(),
            new UcuRot(),
            new UcuDrop(),
            new UcuExit(),
            new UcuLen(),
            new UcuAt(),
            new UcuSet(),
            new UcuReturn(),
            new UcuPrint(),
            new UcuPrintLn(),
        });
        
        instructionMap.putAll(commandsToMap(extraCommands));

        context = new UcuContext();

        var parser = new UcuLangParser(src);
        for (var token = parser.next(); token != null; token = parser.next()) {
            switch (token.type) {
                case Comment -> { /* Ignora Comentarios */}
                case Label -> {
                    if (context.getLabel(token.token) == null) {
                        context.setLabel(token.token, compiledInstructions.size());
                    } else {
                        throw new RuntimeException("Etiqueta duplicada: " + token.token);
                    }
                }
                case Call -> compiledInstructions.add(new UcuCall(token.token));
                case Jump -> compiledInstructions.add(new UcuJump(token.token));
                case Number -> compiledInstructions.add(new UcuPushValue(new UcuValue(Double.valueOf(token.token))));
                case StrLiteral -> compiledInstructions.add(new UcuPushValue(new UcuValue(token.token)));
                case EmptyArray -> compiledInstructions.add(new UcuPushValue(new UcuValue(new LinkedList<>())));
                case VariableDefinition -> compiledInstructions.add(new UcuDefineVariable(token.token));
                case VariablePush -> compiledInstructions.add(new UcuPushVariable(token.token));
                case Command -> {
                    UcuInstruction instruction = instructionMap.get(token.token);
                    if (instruction == null) {
                        throw new RuntimeException("Unknown instruction: " + token.token);
                    }
                    compiledInstructions.add(instruction);
                }
            }
        }

        instructions = compiledInstructions.toArray(UcuInstruction[]::new);
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public boolean next() {
        if (context.getProgramCounter() < instructions.length) {
            UcuInstruction instruction = instructions[context.getProgramCounter()];
            if (instruction == null) {
                context.nextInstruction();
            } else {
                instruction.execute(context);
            }
            return true;
        } else {
            return false;
        }
    }

    private HashMap<String, UcuCommand> commandsToMap(UcuCommand[] commands) {
        var result = new HashMap<String, UcuCommand>();

        for (var cmd : commands) {
            result.put(cmd.getCommandName(), cmd);
        }

        return result;
    }
}
