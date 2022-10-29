package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuJump implements UcuInstruction {
    public final String label;

    public UcuJump(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "UcuJump(" + label + ")";
    }

    @Override
    public void execute(UcuContext context) {
        context.jumpTo(label);
    }
}
