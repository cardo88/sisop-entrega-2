package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuExit implements UcuInstruction {
    @Override
    public String toString() {
        return "UcuExit(exit)";
    }

    @Override
    public void execute(UcuContext context) {
        context.end();
    }
}
