package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuNotEquals implements UcuCommand {
    @Override
    public String toString() {
        return "UcuNotEquals(!=)";
    }

    @Override
    public String getCommandName() {
        return "!=";
    }

    @Override
    public void execute(UcuContext context) {
        UcuValue second = context.popValue();
        UcuValue first = context.popValue();

        if (first.notEqual(second)) {
            context.nextInstruction();
        } else {
            context.skipOneInstruction();
        }
    }
}
