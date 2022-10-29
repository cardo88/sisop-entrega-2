package com.sisop.sisop.UcuLang;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 */
public class UcuLang {
    private final UcuInstruction[] instructions;
    private final UcuContext context = new UcuContext();

    public UcuLang(String src) {
        instructions = compile(src);
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

    private UcuInstruction[] compile(String src) {
        LinkedList<UcuInstruction> compiledInstructions = new LinkedList<>();

        var instructionMap = new HashMap<String, UcuInstruction>();

        instructionMap.put("+",         new UcuAdd());
        instructionMap.put("-",         new UcuSub());
        instructionMap.put("*",         new UcuMul());
        instructionMap.put("/",         new UcuDiv());
        instructionMap.put("+",         new UcuAdd()); 
        instructionMap.put("-",         new UcuSub()); 
        instructionMap.put("*",         new UcuMul()); 
        instructionMap.put("/",         new UcuDiv()); 
        instructionMap.put(">",         new UcuGraterThan()); 
        instructionMap.put(">=",        new UcuGraterThan()); 
        instructionMap.put("<",         new UcuLessThan()); 
        instructionMap.put("<=",        new UcuLessThanOrEqual()); 
        instructionMap.put("duplicate", new UcuDuplicate()); 
        instructionMap.put("swap",      new UcuSwap()); 
        instructionMap.put("over",      new UcuOver()); 
        instructionMap.put("drop",      new UcuDrop()); 
        instructionMap.put("exit",      new UcuExit()); 
        instructionMap.put("return",    new UcuReturn()); 
        instructionMap.put("print",     new UcuPrint()); 
        instructionMap.put("println",   new UcuPrintLn()); 

        for (String line : src.split("\n")) {
            var parser = new UcuLangParser(line);
            for (var token = parser.next(); token != null; token = parser.next()) {

                switch (token.type) {
                    case Comment -> { /* Ignora Comentarios */}
                    case Label -> context.setLabel(token.token, compiledInstructions.size());
                    case Call -> compiledInstructions.add(new UcuCall(token.token));
                    case Jump -> compiledInstructions.add(new UcuJump(token.token));
                    case Number -> compiledInstructions.add(new UcuPushValue(new UcuValue(Double.valueOf(token.token))));
                    case StrLiteral -> compiledInstructions.add(new UcuPushValue(new UcuValue(token.token)));
                    case VariableDefinition -> compiledInstructions.add(new UcuDefineVariable(token.token));
                    case VariablePush -> compiledInstructions.add(new UcuPushVariable(token.token));
                    case Command -> {
                        UcuInstruction ins = instructionMap.get(token.token);
                        if (ins == null) {
                            System.out.println("ERROR: Unkown instruction: " + token.token);        
                        }
                        compiledInstructions.add(instructionMap.get(token.token));
                    }
                }
            }
        }

        return compiledInstructions.toArray(UcuInstruction[]::new);
    }
}
