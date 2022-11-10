package ucunix.sdk;

import ucunix.Process;
import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.UcuString;

public class ProcessGetState implements UcuCommand {

    @Override
    public String getCommandName() {
        return "process.get-state";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        if (value instanceof Process process) {
            context.pushValue(new UcuString(process.getState().toString()));
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
