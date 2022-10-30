package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuLen implements UcuCommand {
    @Override
    public String toString() {
        return "UcuLen(len)";
    }

    @Override
    public String getCommandName() {
        return "len";
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        context.pushValue(value.len());
        context.nextInstruction();
    }
}
