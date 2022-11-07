package com.sisop.sisop.OS.Sdk.Semaphore;

import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.OS.Scheduler;
import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;

public class UcunixSemaphoreP implements UcuCommand {
    private final ProcessId pid;
    private final Scheduler scheduler;

    public UcunixSemaphoreP(ProcessId pid, Scheduler scheduler) {
        this.pid = pid;
        this.scheduler = scheduler;
    }

    @Override
    public String getCommandName() {
        return "semaphore.p";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();

        if (value instanceof UcunixSemaphore semaphore) {
            semaphore.P(pid, scheduler);
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
