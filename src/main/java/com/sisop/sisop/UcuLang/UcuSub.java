package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuSub implements UcuInstruction {
    @Override
    public String toString() {
        return "UcuSub(-)";
    }

    @Override
    public void execute(UcuContext context) {
        UcuValue second = context.popValue();
        UcuValue first = context.popValue();
        context.pushValue(first.sub(second));
        context.nextInstruction();
    }
}
