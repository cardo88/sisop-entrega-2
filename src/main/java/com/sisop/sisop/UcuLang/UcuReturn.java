package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuReturn implements UcuCommand {
    @Override
    public String toString() {
        return "UcuReturn()";
    }

    @Override
    public String getCommandName() {
        return "return";
    }

    @Override
    public void execute(UcuContext context) {
        context.ret();
    }
}
