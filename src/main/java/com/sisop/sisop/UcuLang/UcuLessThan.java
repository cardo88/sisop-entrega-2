package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuLessThan implements UcuCommand {
    @Override
    public String toString() {
        return "UcuLessThan(<)";
    }

    @Override
    public String getCommandName() {
        return "<";
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
