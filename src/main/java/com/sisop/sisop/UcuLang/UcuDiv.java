package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuDiv implements UcuInstruction {
    @Override
    public String toString() {
        return "UcuDiv(/)";
    }

    @Override
    public void execute(UcuContext context) {
        UcuValue second = context.popValue();
        UcuValue first = context.popValue();
        context.pushValue(first.div(second));
        context.nextInstruction();
    }
}
