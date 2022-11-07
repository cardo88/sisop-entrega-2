package com.sisop.sisop.UcuLang.Commands;

import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.UcuInstruction;

/**
 * 
 */
public class UcuPushVariable implements UcuInstruction {
    private final String variableName;

    public UcuPushVariable(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return "PushVariable: " + variableName;
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.getVariable(variableName);
        if (value == null) {
            throw new RuntimeException("Variable no definida: " + variableName);
        }
        context.pushValue(value);
    }
}
