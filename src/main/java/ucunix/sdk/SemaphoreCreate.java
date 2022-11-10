package ucunix.sdk;

import ucunix.ProcessId;
import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.UcuNumber;
import uculang.UcuString;

public class SemaphoreCreate implements UcuCommand {
    private final ProcessId pid;

    public SemaphoreCreate(ProcessId pid) {
        this.pid = pid;
    }

    @Override
    public String getCommandName() {
        return "semaphore.create";
    }

    @Override
    public void execute(UcuContext context) {
        var capacityValue = context.popValue();
        var nameValue = context.popValue();

        if (capacityValue instanceof UcuNumber capacity && nameValue instanceof UcuString nameString) {
            var name = nameString.toString();

            var semaphore = SharedVariables.getOrCreate(pid, name, new Semaphore(name, capacity.intValue()));

            context.pushValue(semaphore);
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
