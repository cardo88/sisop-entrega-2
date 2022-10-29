package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuLessThanOrEqual implements UcuCommand {
    @Override
    public String toString() {
        return "UcuGreaterThanOrEqual(<=)";
    }

    @Override
    public String getCommandName() {
        return "<=";
    }

    @Override
    public void execute(UcuContext context) {
        UcuValue second = context.popValue();
        UcuValue first = context.popValue();

        if (first.lessThanOrEqual(second)) {
            context.nextInstruction();
        } else {
            context.skipOneInstruction();
        }
    }
}
