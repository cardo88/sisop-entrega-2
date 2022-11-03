package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuSet implements UcuCommand {
    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public String getCommandName() {
        return "set";
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        var index = context.popValue();
        var list = context.popValue();
        list.set(index.asNumber().intValue(), value);
        context.nextInstruction();
    }
}
