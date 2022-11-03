package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuAppend implements UcuCommand {
    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public String getCommandName() {
        return "append";
    }

    @Override
    public void execute(UcuContext context) {
        UcuValue second = context.popValue();
        UcuValue first = context.popValue();
        first.append(second);
        context.pushValue(first);
        context.nextInstruction();
    }
}
