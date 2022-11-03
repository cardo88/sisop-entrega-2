package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuAt implements UcuCommand {
    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public String getCommandName() {
        return "at";
    }

    @Override
    public void execute(UcuContext context) {
        var index = context.popValue();
        var value = context.popValue();
        var x = value.get(index.asNumber().intValue());
        context.pushValue(x);
        context.nextInstruction();
    }
}
