package com.sisop.sisop.UcuLang;

/**
 * 
 */
public class UcuDefineVariable implements UcuInstruction {
    private final String variableName;

    public UcuDefineVariable(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return "define variable (" + variableName + ")";
    }

    @Override
    public void execute(UcuContext context) {
        context.setVariable(variableName, context.popValue());
        context.nextInstruction();
    }
}
