package com.sisop.sisop.UcuLang.Commands;

import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;

/**
 *
 */
public class UcuReturn implements UcuCommand {
    @Override
    public String getCommandName() {
        return "return";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        context.ret();
    }
}
