package com.sisop.sisop.UcuLang.Commands;

import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;

/**
 *
 */
public class UcuEquals implements UcuCommand {
    @Override
    public String getCommandName() {
        return "=";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var second = context.popValue();
        var first = context.popValue();

        if (!(first.equals(second))) {
            context.nextInstruction();
        }
    }
}
