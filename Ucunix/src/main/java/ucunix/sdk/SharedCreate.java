package ucunix.sdk;

import ucunix.ProcessId;
import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.InvalidTypesRuntimeException;
import uculang.UcuString;
import uculang.UcuType;

public class SharedCreate implements UcuCommand {

    private final ProcessId pid;

    public SharedCreate(ProcessId pid) {
        this.pid = pid;
    }

    @Override
    public String getCommandName() {
        return "shared.create";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var seed = context.popValue();
        var name = context.popValue();

        if (name instanceof UcuString n) {
            context.pushValue(SharedVariables.getOrCreate(pid, name.toString(), seed));
        } else {
            throw new InvalidTypesRuntimeException(
                getCommandName(), 
                new String[][] { 
                    { UcuString.class.getName() },
                    { UcuType.class.getName() },
                }, 
                new String[][] { 
                    { name.getClass().getName() },
                    { seed.getClass().getName() },
                }
            );
        }
    }
}
