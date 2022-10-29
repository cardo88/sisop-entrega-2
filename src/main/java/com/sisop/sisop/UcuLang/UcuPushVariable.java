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
        return "UcuPushVariable(" + variableName + ")";
    }

    @Override
    public void execute(UcuContext context) {
        context.pushValue(context.getVariable(variableName));
        context.nextInstruction();
    }
}
