package com.sisop.sisop.UcuLang.Commands;

import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;

/**
 *
 */
public class UcuExit implements UcuCommand {
    @Override
    public String getCommandName() {
        return "exit";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        context.setFinished();
    }
}
