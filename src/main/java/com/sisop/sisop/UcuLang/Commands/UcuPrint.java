package com.sisop.sisop.UcuLang.Commands;

import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;

/**
 *
 */
public class UcuPrint implements UcuCommand {
    @Override
    public String getCommandName() {
        return "print";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        System.out.print(context.popValue().toString());
    }
}
