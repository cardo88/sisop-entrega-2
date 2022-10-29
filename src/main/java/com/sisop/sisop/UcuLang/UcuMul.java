package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuMul implements UcuInstruction {
    @Override
    public String toString() {
        return "UcuMul(*)";
    }

    @Override
    public void execute(UcuContext context) {
        UcuValue second = context.popValue();
        UcuValue first = context.popValue();
        context.pushValue(first.mul(second));
        context.nextInstruction();
    }
}
