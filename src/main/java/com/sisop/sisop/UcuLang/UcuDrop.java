package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuDrop implements UcuCommand {
    @Override
    public String toString() {
        return "UcuDrop(drop)";
    }

    @Override
    public String getCommandName() {
        return "drop";
    }

    @Override
    public void execute(UcuContext context) {
        context.popValue();
        context.nextInstruction();
    }
}
