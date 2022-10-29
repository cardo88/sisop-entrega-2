package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuDrop implements UcuInstruction {
    @Override
    public String toString() {
        return "UcuDrop(drop)";
    }

    @Override
    public void execute(UcuContext context) {
        context.popValue();
        context.nextInstruction();
    }
}
