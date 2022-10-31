package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuSet implements UcuCommand {
    @Override
    public String toString() {
        return "UcuSet(set)";
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
        list.set((int)((double) index.value), value);
        context.nextInstruction();
    }
}
