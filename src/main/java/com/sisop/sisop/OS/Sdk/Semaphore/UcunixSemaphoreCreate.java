package com.sisop.sisop.OS.Sdk.Semaphore;

import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.Types.UcuNumber;
import com.sisop.sisop.UcuLang.Types.UcuString;

public class UcunixSemaphoreCreate implements UcuCommand {
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
            context.pushValue(UcunixSemaphore.create(name, capacity.intValue()));
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
