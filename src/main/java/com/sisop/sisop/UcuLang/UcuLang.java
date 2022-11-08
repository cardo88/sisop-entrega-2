package com.sisop.sisop.UcuLang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sisop.sisop.UcuLang.Commands.*;
import com.sisop.sisop.UcuLang.Exceptions.DuplicatedLabel;
import com.sisop.sisop.UcuLang.Exceptions.DuplicatedLocalLabel;
import com.sisop.sisop.UcuLang.Exceptions.LocalLabelWithoutParent;
import com.sisop.sisop.UcuLang.Exceptions.LocalVariableWithoutParent;
import com.sisop.sisop.UcuLang.Exceptions.UnknownCommand;
import com.sisop.sisop.UcuLang.Types.UcuCopyOp;

/**
 * 
 */
public class UcuLang {

    /**
     *
     * @param src
     * @return
     */
    public static UcuProgram compile(String src) 
            throws UnknownCommand, 
                   LocalLabelWithoutParent,
                   LocalVariableWithoutParent,
                   DuplicatedLabel,
                   DuplicatedLocalLabel
    {
        return UcuLang.compile(src, Arrays.asList(new UcuCommand[0]));
    }

    /**
     *
     * @param src
     * @param extraCommands
     * @return
     */
    public static UcuProgram compile(String src, List<UcuCommand> extraCommands) 
            throws UnknownCommand, 
                   LocalLabelWithoutParent,
                   LocalVariableWithoutParent,
                   DuplicatedLabel,
                   DuplicatedLocalLabel
    {
        var instructionMap = commandsToMap(Arrays.asList(
            new UcuCommand[] {
                // Operadores
                new UcuAdd(),
                new UcuAppend(),
                new UcuGet(),
                new UcuDiv(),
                new UcuLen(),
                new UcuMul(),
                new UcuMod(),
                new UcuSet(),
                new UcuSub(),
                new UcuAssign(),
                // Comparadores
                new UcuEquals(),
                new UcuGraterThan(),
                new UcuGreaterThanOrEqual(),
                new UcuLessThan(),
                new UcuLessThanOrEqual(),
                new UcuNotEquals(),
                // Operaciones de pila
                new UcuOver(),
                new UcuRot(),
                new UcuSwap(),
                new UcuDrop(),
                new UcuDup(),
                // Otros comandos
                new UcuExit(),
                new UcuPrint(),
                new UcuPrintLn(),
                new UcuRandom(),
                new UcuReturn(),
            }
        ));

        instructionMap.putAll(commandsToMap(extraCommands));

        var instructions = new ArrayList<UcuInstruction>();
        var labels = new HashMap<String, Integer>();
        var currentTopLevelLabel = "";

        var parser = new UcuLangParser(src);
        for (var token = parser.next(); token != null; token = parser.next()) {
            switch (token.type) {
                case Whitespace, Comment -> {
                    /* Ignora Comentarios y espacios */
                }
                case LocalLabel -> {
                    var label = getLocalLabelAbsoluteName(currentTopLevelLabel, token.token);
                    if (labels.get(label) == null) {
                        labels.put(label, instructions.size());
                    } else {
                        throw new DuplicatedLocalLabel(currentTopLevelLabel, token.token);
                    }
                }
                case Label -> {
                    var label = token.token;
                    if (labels.get(token.token) == null) {
                        currentTopLevelLabel = label;
                        labels.put(label, instructions.size());
                    } else {
                        throw new DuplicatedLabel(label);
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
                case Number -> instructions.add(new UcuPushValue((UcuCopyOp) token.value));
                case StrLiteral -> instructions.add(new UcuPushValue((UcuCopyOp) token.value));
                case EmptyArray -> instructions.add(new UcuPushValue((UcuCopyOp) token.value));
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
                        throw new UnknownCommand(token.token);
                    }
                    instructions.add(instruction);
                }
            }
        }

        return new UcuProgram(src, instructions, labels);
    }

    private static String joinIdentifiers(String a, String b) {
        return a + " | " + b;
    }

    private static String getLocalLabelAbsoluteName(String currentTopLevelLabel, String localLabel) throws LocalLabelWithoutParent {
        if (currentTopLevelLabel.isEmpty()) {
            throw new LocalLabelWithoutParent(localLabel);
        }
        return joinIdentifiers(currentTopLevelLabel, localLabel);
    }

    private static String getLocalVariableAbsoluteName(String currentTopLevelLabel, String localVariable) throws LocalVariableWithoutParent {
        if (currentTopLevelLabel.isEmpty()) {
            throw new LocalVariableWithoutParent(localVariable);
        }

        return joinIdentifiers(currentTopLevelLabel, localVariable);
    }

    private static Map<String, UcuCommand> commandsToMap(List<UcuCommand> commands) {
        var result = new HashMap<String, UcuCommand>();

        for (var cmd : commands) {
            result.put(cmd.getCommandName(), cmd);
        }

        return result;
    }
}
