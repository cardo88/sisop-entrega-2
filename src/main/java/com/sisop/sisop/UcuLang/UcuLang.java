package com.sisop.sisop.UcuLang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 */
public class UcuLang {
    public enum StepMode {
        Play,
        Stop,
        PlayOne,
    }

    public interface DebuggerCallback {
        public void onStepModeChange(StepMode pre, StepMode post);
    }

    // Lista de instrucciones del programa compilado.
    private ArrayList<UcuInstruction> instructions; 

    // Código fuente original
    private String sourceCode; 

    // Contexto de ejecución del programa
    private UcuContext context = new UcuContext();

    private StepMode stepMode = StepMode.Play;

    private LinkedList<DebuggerCallback> debuggerCallback = new LinkedList<>();

    public UcuLang() {
        compile("exit", new UcuCommand[0]);
    }

    public UcuLang(String src) {
        this(src, new UcuCommand[0]);
    }

    public UcuLang(String src, UcuCommand[] extraCommands) {
        compile(src, extraCommands);
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public ArrayList<UcuInstruction> getCompiledInstructions() {
        return instructions;
    }

    public UcuContext getContext() {
        return context;
    }

    public void addDebuggerCallback(DebuggerCallback callback) {
        debuggerCallback.add(callback);
    }

    public void removeDebuggerCallback(DebuggerCallback callback) {
        debuggerCallback.remove(callback);
    }

    public void clearAllDebuggerCallbacks() {
        debuggerCallback.clear();
    }

    public void setStepMode(StepMode mode) {
        var pre = stepMode;
        stepMode = mode;
        for (var cb : debuggerCallback) {
            cb.onStepModeChange(pre, mode);
        }
    }

    public StepMode getStepMode() {
        return stepMode;
    }

    public boolean next() {
        if (context.getProgramCounter() < instructions.size()) {
            switch (stepMode) {
                case Stop -> { /* no-op */ }
                case Play -> runNextInstruction();
                case PlayOne -> {
                    runNextInstruction();
                    setStepMode(StepMode.Stop);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private void runNextInstruction() {
        UcuInstruction instruction = instructions.get(context.getProgramCounter());
        if (instruction == null) {
            context.nextInstruction();
        } else {
            instruction.execute(context);
        }
    }

    public void compile(String src, UcuCommand[] extraCommands) {
        sourceCode = src;

        instructions = new ArrayList<>();

        String currentTopLevelLabel = "";

        var instructionMap = commandsToMap(new UcuCommand[] {
            new UcuAdd(),
            new UcuAppend(),
            new UcuAt(),
            new UcuDiv(),
            new UcuDrop(),
            new UcuDup(),
            new UcuEquals(),
            new UcuExit(),
            new UcuGraterThan(),
            new UcuGreaterThanOrEqual(),
            new UcuLen(),
            new UcuNewline(),
            new UcuLessThan(),
            new UcuLessThanOrEqual(),
            new UcuMul(),
            new UcuNotEquals(),
            new UcuOver(),
            new UcuPrint(),
            new UcuPrintLn(),
            new UcuRandom(),
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
                    var label = getLocalLabelAbsoluteName(currentTopLevelLabel, token.token);
                    if (context.getLabel(label) == null) {
                        context.setLabel(label, instructions.size());
                    } else {
                        throw new RuntimeException(
                            "Etiqueta local duplicada: " + token.token + 
                            " bajo la etiqueta: " + currentTopLevelLabel
                        );
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
                    instructions.add(new UcuCall(getLocalLabelAbsoluteName(currentTopLevelLabel, token.token)));
                }
                case Jump -> {
                    instructions.add(new UcuJump(token.token));
                }
                case LocalJump -> {
                    instructions.add(new UcuJump(getLocalLabelAbsoluteName(currentTopLevelLabel, token.token)));
                }
                case Number -> instructions.add(new UcuPushValue(new UcuValue(new UcuNumber(Double.valueOf(token.token)))));
                case StrLiteral -> instructions.add(new UcuPushValue(new UcuValue(new UcuString(token.token))));
                case EmptyArray -> instructions.add(new UcuPushValue(new UcuValue(new UcuList())));
                case VariableDefinition -> instructions.add(new UcuDefineVariable(token.token));
                case VariablePush -> instructions.add(new UcuPushVariable(token.token));
                case LocalVariableDefinition -> {
                    instructions.add(new UcuDefineVariable(getLocalVariableAbsoluteName(currentTopLevelLabel, token.token)));
                }
                case LocalVariablePush -> {
                    instructions.add(new UcuPushVariable(getLocalVariableAbsoluteName(currentTopLevelLabel, token.token)));
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

    private String getLocalLabelAbsoluteName(String currentTopLevelLabel, String localLabel) {
        if (currentTopLevelLabel.isEmpty()) {
            throw new RuntimeException("Se intentó utilizar una etiqueta local sin una etiqueta padre: " + localLabel);
        }
        return currentTopLevelLabel + " " + localLabel;
    }

    private String getLocalVariableAbsoluteName(String currentTopLevelLabel, String localVariable) {
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
