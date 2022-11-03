package com.sisop.sisop.UcuLang;

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
        return "push variable (" + variableName + ")";
    }

    @Override
    public void execute(UcuContext context) {
        UcuValue value = context.getVariable(variableName);
        if (value == null) {
            throw new RuntimeException("Variable no definida: " + variableName);
        }
        context.pushValue(value);
        context.nextInstruction();
    }
}
