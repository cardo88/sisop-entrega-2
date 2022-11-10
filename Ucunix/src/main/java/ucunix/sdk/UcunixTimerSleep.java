package ucunix.sdk;

import ucunix.ProcessId;
import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.UcuNumber;

public class UcunixTimerSleep implements UcuCommand {
    private final UcunixTimer ucunixTimer;
    private final ProcessId processId;

    public UcunixTimerSleep(ProcessId pid, UcunixTimer timer) {
        this.ucunixTimer = timer;
        this.processId = pid;
    }

    @Override
    public String getCommandName() {
        return "timer.sleep";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        if (value instanceof UcuNumber number) {
            ucunixTimer.sleepProcess(processId, number.intValue());
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
