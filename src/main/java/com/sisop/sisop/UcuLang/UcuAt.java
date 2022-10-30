package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuAt implements UcuCommand {
    @Override
    public String toString() {
        return "UcuAt(at)";
    }

    @Override
    public String getCommandName() {
        return "at";
    }

    @Override
    public void execute(UcuContext context) {
        var index = context.popValue();
        var value = context.popValue();
        context.pushValue(value.at((int)((double) index.value)));
        context.nextInstruction();
    }
}
