package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuPrint implements UcuCommand {
    @Override
    public String toString() {
        return "UcuPrint(print)";
    }

    @Override
    public String getCommandName() {
        return "print";
    }

    @Override
    public void execute(UcuContext context) {
        System.out.print(context.popValue().value.toString());
        context.nextInstruction();
    }
}
