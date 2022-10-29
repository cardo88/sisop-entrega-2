package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuReturn implements UcuInstruction {
    @Override
    public String toString() {
        return "UcuReturn()";
    }

    @Override
    public void execute(UcuContext context) {
        context.ret();
    }
}
