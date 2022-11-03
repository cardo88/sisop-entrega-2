package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuPushValue implements UcuInstruction {
    private final UcuValue value;

    public UcuPushValue(UcuValue value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "push (" + value.toString() + ")";
    }

    @Override
    public void execute(UcuContext context) {
        context.pushValue(value);
        context.nextInstruction();
    }
}
