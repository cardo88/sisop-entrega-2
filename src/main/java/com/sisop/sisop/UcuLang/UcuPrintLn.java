package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuPrintLn implements UcuCommand {
    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public String getCommandName() {
        return "println";
    }

    @Override
    public void execute(UcuContext context) {
        System.out.println(context.popValue().value.toString());
        context.nextInstruction();
    }
}
