package ucunix.sdk;

import ucunix.ProcessId;
import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.UcuNumber;

public class ProcessPid implements UcuCommand {
    private final ProcessId pid;

    public ProcessPid(ProcessId pid) {
        this.pid = pid;
    }

    @Override
    public String getCommandName() {
        return "process.pid";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        context.pushValue(new UcuNumber(pid.getId()));
    }
}
