package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuRandom implements UcuCommand {
    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public String getCommandName() {
        return "random";
    }

    @Override
    public void execute(UcuContext context) {
        context.pushValue(new UcuValue(new UcuNumber(Math.random())));
        context.nextInstruction();
    }
}
