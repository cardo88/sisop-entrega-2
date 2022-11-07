package com.sisop.sisop.OS.Sdk.Timer;

import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.Types.UcuNumber;

public class UcunixTimerSleep implements UcuCommand {
    private final UcunixTimer ucunixTimer;
    private final ProcessId processId;

    public UcunixTimerSleep(ProcessId pid, UcunixTimer timer) {
        this.ucunixTimer = timer;
        this.processId = pid;
    }

    @Override
    public String getCommandName() {
        return "timer.sleep";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        if (value instanceof UcuNumber number) {
            ucunixTimer.sleepProcess(processId, number.intValue());
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
