package ucunix.sdk;

import ucunix.ProcessId;
import uculang.UcuCommand;
import uculang.UcuContext;
import ucunix.Scheduler;

public class ProcessSelf implements UcuCommand {
    private final ProcessId pid;
    private final Scheduler scheduler;

    public ProcessSelf(ProcessId pid, Scheduler scheduler) {
        this.pid = pid;
        this.scheduler = scheduler;
    }

    @Override
    public String getCommandName() {
        return "process.self";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var process = scheduler.getProcess(pid);
        if (process != null) {
            context.pushValue(process);
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
