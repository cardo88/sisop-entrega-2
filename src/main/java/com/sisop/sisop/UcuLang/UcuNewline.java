package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuNewline implements UcuCommand {
    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public String getCommandName() {
        return "newline";
    }

    @Override
    public void execute(UcuContext context) {
        context.pushValue(new UcuValue(new UcuString("\n")));
        context.nextInstruction();
    }
}
