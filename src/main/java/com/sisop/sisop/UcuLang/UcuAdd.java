package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuAdd implements UcuCommand {
    @Override
    public String getCommandName() {
        return "+";
    }

    @Override
    public void execute(UcuContext context) {
        UcuValue second = context.popValue();
        UcuValue first = context.popValue();
        context.pushValue(first.add(second));
        context.nextInstruction();
    }

    @Override
    public String toString() {
        return "UcuAdd(+)";
    }
}
