package com.sisop.sisop.OS.Sdk.Semaphore;

import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.OS.Sdk.SharedVariables.UcunixSharedVariables;
import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.Types.UcuNumber;
import com.sisop.sisop.UcuLang.Types.UcuString;

public class UcunixSemaphoreCreate implements UcuCommand {
    private final ProcessId pid;

    public UcunixSemaphoreCreate(ProcessId pid) {
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

            var semaphore = UcunixSharedVariables.getOrCreate(pid, name, new UcunixSemaphore(name, capacity.intValue()));

            context.pushValue(semaphore);
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
