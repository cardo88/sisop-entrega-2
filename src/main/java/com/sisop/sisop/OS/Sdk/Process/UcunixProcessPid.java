package com.sisop.sisop.OS.Sdk.Process;

import com.sisop.sisop.OS.Console;
import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.Types.UcuNumber;

public class UcunixProcessPid implements UcuCommand {
    private final ProcessId pid;

    public UcunixProcessPid(ProcessId pid) {
        this.pid = pid;
    }

    @Override
    public String getCommandName() {
        return "process.pid";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        context.pushValue(new UcuNumber(pid.getId()));
    }
}
