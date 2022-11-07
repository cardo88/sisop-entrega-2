package com.sisop.sisop.OS.Sdk.Semaphore;

import com.sisop.sisop.OS.Scheduler;
import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;

public class UcunixSemaphoreV implements UcuCommand {
    private final Scheduler scheduler;

    public UcunixSemaphoreV(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public String getCommandName() {
        return "semaphore.v";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();

        if (value instanceof UcunixSemaphore semaphore) {
            semaphore.V(scheduler);
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
