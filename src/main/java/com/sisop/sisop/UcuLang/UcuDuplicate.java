package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuDuplicate implements UcuCommand {
    @Override
    public String toString() {
        return "UcuDuplicate(duplicate)";
    }

    @Override
    public String getCommandName() {
        return "duplicate";
    }

    @Override
    public void execute(UcuContext context) {
        context.pushValue(context.peekValue());
        context.nextInstruction();
    }
}
