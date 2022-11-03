package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuDup implements UcuCommand {
    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public String getCommandName() {
        return "dup";
    }

    @Override
    public void execute(UcuContext context) {
        context.pushValue(context.peekValue().duplicate());
        context.nextInstruction();
    }
}
