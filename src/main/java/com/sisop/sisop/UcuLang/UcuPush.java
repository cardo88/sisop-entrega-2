package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuPush implements UcuCommand {
    @Override
    public String toString() {
        return "UcuPush(push)";
    }

    @Override
    public String getCommandName() {
        return "push";
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        var array = context.popValue();
        context.pushValue(array.add(value));
        context.nextInstruction();
    }
}
