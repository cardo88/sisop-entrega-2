package ucunix.sdk;

import ucunix.Process;

import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.UcuNumber;

public class ProcessGetPid implements UcuCommand {

    @Override
    public String getCommandName() {
        return "process.get-pid";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        if (value instanceof Process process) {
            context.pushValue(new UcuNumber(process.getPid().getId()));
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
