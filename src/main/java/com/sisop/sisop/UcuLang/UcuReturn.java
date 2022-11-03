package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuReturn implements UcuCommand {
    @Override
    public String toString() {
        return getCommandName();
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
