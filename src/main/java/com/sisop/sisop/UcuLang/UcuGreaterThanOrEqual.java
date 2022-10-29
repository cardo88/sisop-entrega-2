package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuGreaterThanOrEqual implements UcuCommand {
    @Override
    public String toString() {
        return "UcuGreaterThanOrEqual(>=)";
    }

    @Override
    public String getCommandName() {
        return ">=";
    }

    @Override
    public void execute(UcuContext context) {
        UcuValue second = context.popValue();
        UcuValue first = context.popValue();

        if (first.greaterThanOrEqual(second)) {
            context.nextInstruction();
        } else {
            context.skipOneInstruction();
        }
    }
}
