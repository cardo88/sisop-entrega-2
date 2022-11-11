package ucunix.sdk;

import ucunix.Process;
import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.UcuList;
import uculang.UcuString;
import uculang.UcuType;

public class ProcessGetParameters implements UcuCommand {

    @Override
    public String getCommandName() {
        return "process.get-parameters";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        if (value instanceof Process process) {
            context.pushValue(new UcuList(process.getParameters().stream().map(x -> (UcuType) new UcuString(x)).toList()));
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
