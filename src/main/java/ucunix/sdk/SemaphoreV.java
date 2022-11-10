package ucunix.sdk;

import ucunix.Scheduler;
import uculang.UcuCommand;
import uculang.UcuContext;

public class SemaphoreV implements UcuCommand {
    private final Scheduler scheduler;

    public SemaphoreV(Scheduler scheduler) {
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

        if (value instanceof Semaphore semaphore) {
            semaphore.V(scheduler);
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
