package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuExit implements UcuCommand {
    @Override
    public String toString() {
        return "UcuExit(exit)";
    }

    @Override
    public String getCommandName() {
        return "exit";
    }

    @Override
    public void execute(UcuContext context) {
        context.end();
    }
}
