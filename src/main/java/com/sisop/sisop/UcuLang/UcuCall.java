package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuCall implements UcuInstruction {
    public final String label;

    public UcuCall(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "call (" + label + ")";
    }

    @Override
    public void execute(UcuContext context) {
        context.nextInstruction();
        context.call(label);
    }
}
