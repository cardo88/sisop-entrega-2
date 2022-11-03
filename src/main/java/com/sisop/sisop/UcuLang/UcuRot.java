package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuRot implements UcuCommand {
    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public String getCommandName() {
        return "rot";
    }

    @Override
    public void execute(UcuContext context) {
        var c = context.popValue();
        var b = context.popValue();
        var a = context.popValue();
        context.pushValue(b);
        context.pushValue(c);
        context.pushValue(a);
        context.nextInstruction();
    }
}
