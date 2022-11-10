package ucunix.sdk;

import ucunix.ProcessId;
import ucunix.Scheduler;
import uculang.UcuCommand;
import uculang.UcuContext;

public class ConsolePrintln implements UcuCommand {
    private final ProcessId pid;
    private final Scheduler scheduler;

    public ConsolePrintln(ProcessId pid, Scheduler scheduler) {
        this.pid = pid;
        this.scheduler = scheduler;
    }

    @Override
    public String getCommandName() {
        return "console.println";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var process = scheduler.getProcess(pid);
        if (process != null) {
            var value = context.popValue();
            process.getConsole().print(value.toString());
            process.getConsole().print("\n");
        }
    }
}
