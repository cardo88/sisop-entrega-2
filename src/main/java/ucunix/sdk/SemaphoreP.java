package ucunix.sdk;

import ucunix.ProcessId;
import ucunix.Scheduler;
import uculang.UcuCommand;
import uculang.UcuContext;

public class SemaphoreP implements UcuCommand {
    private final ProcessId pid;
    private final Scheduler scheduler;

    public SemaphoreP(ProcessId pid, Scheduler scheduler) {
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

        if (value instanceof Semaphore semaphore) {
            semaphore.P(pid, scheduler);
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
