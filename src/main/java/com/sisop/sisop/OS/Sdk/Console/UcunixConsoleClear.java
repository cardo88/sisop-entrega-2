package com.sisop.sisop.OS.Sdk.Console;

import com.sisop.sisop.OS.Console;
import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;

public class UcunixConsoleClear implements UcuCommand {
    private final Console console;

    public UcunixConsoleClear(Console console) {
        this.console = console;
    }

    @Override
    public String getCommandName() {
        return "console.clear";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        console.clear();
    }
}
