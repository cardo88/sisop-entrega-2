package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuGreaterThanOrEqual implements UcuCommand {
    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public String getCommandName() {
        return ">=";
    }

    @Override
    public void execute(UcuContext context) {
        UcuValue second = context.popValue();
        UcuValue first = context.popValue();

        if (first.compareTo(second) >= 0) {
            context.nextInstruction();
        } else {
            context.skipOneInstruction();
        }
    }
}
