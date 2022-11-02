package com.sisop.sisop.UcuLang;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 */
public class UcuLang {
    // Lista de instrucciones del programa compilado.
    private ArrayList<UcuInstruction> instructions; 

    // Contexto de ejecución del programa
    private UcuContext context = new UcuContext();

    private String currentTopLevelLabel = "";

    // Código fuente original
    private String sourceCode; 

    public UcuLang() {
        compile("exit", new UcuCommand[0]);
    }

    public UcuLang(String src) {
        this(src, new UcuCommand[0]);
    }

    public UcuLang(String src, UcuCommand[] extraCommands) {
        compile(src, extraCommands);
    }

    private void compile(String src, UcuCommand[] extraCommands) {
        sourceCode = src;

        instructions = new ArrayList<>();

        var instructionMap = commandsToMap(new UcuCommand[] {
            new UcuAdd(),
            new UcuAt(),
            new UcuDiv(),
            new UcuDrop(),
            new UcuDup(),
            new UcuEquals(),
            new UcuExit(),
            new UcuGraterThan(),
            new UcuGreaterThanOrEqual(),
            new UcuLen(),
            new UcuLessThan(),
            new UcuLessThanOrEqual(),
            new UcuMul(),
            new UcuNotEquals(),
            new UcuOver(),
            new UcuPrint(),
            new UcuPrintLn(),
            new UcuReturn(),
            new UcuRot(),
            new UcuSet(),
            new UcuSub(),
            new UcuSwap(),
        });
        
        instructionMap.putAll(commandsToMap(extraCommands));

        context = new UcuContext();

        var parser = new UcuLangParser(src);
        for (var token = parser.next(); token != null; token = parser.next()) {
            switch (token.type) {
                case Comment -> { /* Ignora Comentarios */}
                case LocalLabel -> {
                    var label = getLocalLabelAbsoluteName(token.token);
                    if (context.getLabel(label) == null) {
                        context.setLabel(label, instructions.size());
                    } else {
                        throw new RuntimeException("Etiqueta local duplicada: " + token.token + " bajo la etiqueta: " + currentTopLevelLabel);
                    }
                }
                case Label -> {
                    var label = token.token;
                    if (context.getLabel(token.token) == null) {
                        currentTopLevelLabel = label;
                        context.setLabel(label, instructions.size());
                    } else {
                        throw new RuntimeException("Etiqueta duplicada: " + label);
                    }
                }
                case Call -> {
                    instructions.add(new UcuCall(token.token));
                }
                case LocalCall -> {
                    instructions.add(new UcuCall(getLocalLabelAbsoluteName(token.token)));
                }
                case Jump -> {
                    instructions.add(new UcuJump(token.token));
                }
                case LocalJump -> {
                    instructions.add(new UcuJump(getLocalLabelAbsoluteName(token.token)));
                }
                case Number -> instructions.add(new UcuPushValue(new UcuValue(Double.valueOf(token.token))));
                case StrLiteral -> instructions.add(new UcuPushValue(new UcuValue(token.token)));
                case EmptyArray -> instructions.add(new UcuPushValue(new UcuValue(new ArrayList<>())));
                case VariableDefinition -> instructions.add(new UcuDefineVariable(token.token));
                case VariablePush -> instructions.add(new UcuPushVariable(token.token));
                case LocalVariableDefinition -> {
                    instructions.add(new UcuDefineVariable(getLocalVariableAbsoluteName(token.token)));
                }
                case LocalVariablePush -> {
                    instructions.add(new UcuPushVariable(getLocalVariableAbsoluteName(token.token)));
                }
                case Command -> {
                    UcuInstruction instruction = instructionMap.get(token.token);
                    if (instruction == null) {
                        throw new RuntimeException("Unknown instruction: " + token.token);
                    }
                    instructions.add(instruction);
                }
            }
        }
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public boolean next() {
        if (context.getProgramCounter() < instructions.size()) {
            UcuInstruction instruction = instructions.get(context.getProgramCounter());
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

    private String getLocalLabelAbsoluteName(String localLabel) {
        if (currentTopLevelLabel.isEmpty()) {
            throw new RuntimeException("Se intentó utilizar una etiqueta local sin una etiqueta padre: " + localLabel);
        }
        return currentTopLevelLabel + " " + localLabel;
    }

    private String getLocalVariableAbsoluteName(String localVariable) {
        if (currentTopLevelLabel.isEmpty()) {
            throw new RuntimeException("Se intentó utilizar una variable local sin una etiqueta padre: " + localVariable);
        }
        return currentTopLevelLabel + " " + localVariable;
    }

    private HashMap<String, UcuCommand> commandsToMap(UcuCommand[] commands) {
        var result = new HashMap<String, UcuCommand>();

        for (var cmd : commands) {
            result.put(cmd.getCommandName(), cmd);
        }

        return result;
    }
}
