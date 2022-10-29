package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuAdd implements UcuInstruction {
    @Override
    public String toString() {
        return "UcuAdd(+)";
    }

    @Override
    public void execute(UcuContext context) {
        UcuValue second = context.popValue();
        UcuValue first = context.popValue();
        context.pushValue(first.add(second));
        context.nextInstruction();
    }
}
