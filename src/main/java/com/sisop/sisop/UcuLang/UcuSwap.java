package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuSwap implements UcuCommand {
    @Override
    public String toString() {
        return "UcuSwap(swap)";
    }

    @Override
    public String getCommandName() {
        return "swap";
    }

    @Override
    public void execute(UcuContext context) {
        var second = context.popValue();
        var first = context.popValue();
        context.pushValue(second);
        context.pushValue(first);
        context.nextInstruction();
    }
}
