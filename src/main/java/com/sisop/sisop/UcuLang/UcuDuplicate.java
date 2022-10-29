package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuDuplicate implements UcuInstruction {
    @Override
    public String toString() {
        return "UcuDuplicate(duplicate)";
    }

    @Override
    public void execute(UcuContext context) {
        context.pushValue(context.peekValue());
        context.nextInstruction();
    }
}
