package ucunix.sdk;

import uculang.UcuCommand;
import uculang.UcuContext;
import ucunix.ProcessId;
import ucunix.Scheduler;

public class ConsoleClear implements UcuCommand {
    private final ProcessId pid;
    private final Scheduler scheduler;

    public ConsoleClear(ProcessId pid, Scheduler scheduler) {
        this.pid = pid;
        this.scheduler = scheduler;
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
        var process = scheduler.getProcess(pid);
        if (process != null) {
            process.getConsole().clear();
        }
    }
}
