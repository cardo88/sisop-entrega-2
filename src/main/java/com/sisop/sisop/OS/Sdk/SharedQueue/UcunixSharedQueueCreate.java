package com.sisop.sisop.OS.Sdk.SharedQueue;

import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.Types.UcuNumber;
import com.sisop.sisop.UcuLang.Types.UcuString;

public class UcunixSharedQueueCreate implements UcuCommand {

    @Override
    public String getCommandName() {
        return "sharedQueue.create";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var capacityValue = context.popValue();
        var nameValue = context.popValue();

        if (nameValue instanceof UcuString queueName && capacityValue instanceof UcuNumber capacity) {
            context.pushValue(UcunixSharedQueue.create(queueName.toString(), capacity.intValue()));
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
