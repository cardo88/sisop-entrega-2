package com.sisop.sisop.OS.Sdk.Console;

import com.sisop.sisop.OS.Console;
import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;

public class UcunixConsolePrintln implements UcuCommand {
    private final Console console;

    public UcunixConsolePrintln(Console console) {
        this.console = console;
    }

    @Override
    public String getCommandName() {
        return "console.println";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        console.print(value.toString() + "\n");
    }
}
