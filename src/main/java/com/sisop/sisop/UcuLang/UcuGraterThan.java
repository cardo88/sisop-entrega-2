package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuGraterThan implements UcuInstruction {
    @Override
    public String toString() {
        return "UcuGreaterThan(>)";
    }

    @Override
    public void execute(UcuContext context) {
        UcuValue second = context.popValue();
        UcuValue first = context.popValue();

        if (first.greaterThan(second)) {
            context.nextInstruction();
        } else {
            context.skipOneInstruction();
        }
    }
}
