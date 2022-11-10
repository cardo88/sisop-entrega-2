package ucunix.sdk;

import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.UcuString;
import ucunix.ProcessId;
import ucunix.Scheduler;

public class ConsoleRead implements UcuCommand {
    private final ProcessId pid;
    private final Scheduler scheduler;

    public ConsoleRead(ProcessId pid, Scheduler scheduler) {
        this.pid = pid;
        this.scheduler = scheduler;
    }

    @Override
    public String getCommandName() {
        return "console.read";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var process = scheduler.getProcess(pid);
        if (process != null) {
            context.pushValue(new UcuString(process.getConsole().read()));
        }
    }
}
