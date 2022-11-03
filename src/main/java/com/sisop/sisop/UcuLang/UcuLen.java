package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuLen implements UcuCommand {
    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public String getCommandName() {
        return "len";
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        context.pushValue(new UcuValue(new UcuNumber(value.length())));
        context.nextInstruction();
    }
}
