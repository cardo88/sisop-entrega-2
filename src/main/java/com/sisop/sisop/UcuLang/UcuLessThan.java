package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuLessThan implements UcuInstruction {
    @Override
    public String toString() {
        return "UcuLessThan(<)";
    }

    @Override
    public void execute(UcuContext context) {
        UcuValue second = context.popValue();
        UcuValue first = context.popValue();

        if (first.lessThan(second)) {
            context.nextInstruction();
        } else {
            context.skipOneInstruction();
        }
    }
}
