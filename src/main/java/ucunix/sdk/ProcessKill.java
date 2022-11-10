package ucunix.sdk;

import ucunix.Process;
import ucunix.Scheduler;
import uculang.UcuCommand;
import uculang.UcuContext;

public class ProcessKill implements UcuCommand {
    private final Scheduler scheduler;

    public ProcessKill(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public String getCommandName() {
        return "process.kill";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        if (value instanceof Process process) {
            scheduler.killProcess(process.getPid());
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
