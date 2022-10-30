package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuEquals implements UcuCommand {
    @Override
    public String toString() {
        return "UcuEquals(=)";
    }

    @Override
    public String getCommandName() {
        return "=";
    }

    @Override
    public void execute(UcuContext context) {
        UcuValue second = context.popValue();
        UcuValue first = context.popValue();

        if (first.equal(second)) {
            context.nextInstruction();
        } else {
            context.skipOneInstruction();
        }
    }
}
