package com.sisop.sisop.UcuLang.Commands;

import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;

/**
 *
 */
public class UcuPrintLn implements UcuCommand {
    @Override
    public String getCommandName() {
        return "println";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        System.out.println(context.popValue().toString());
    }
}
