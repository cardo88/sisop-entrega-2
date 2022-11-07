package com.sisop.sisop.OS.Sdk.Debugger;

import com.sisop.sisop.OS.Debugger;
import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;

public class UcunixDebuggerStop implements UcuCommand {

    private final Debugger debugger;
    private final ProcessId pid;

    public UcunixDebuggerStop(Debugger debugger, ProcessId pid) {
        this.debugger = debugger;
        this.pid = pid;
    }

    @Override
    public String getCommandName() {
        return "debugger.stop";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        debugger.attach(pid);
        debugger.pause(pid);
    }
}