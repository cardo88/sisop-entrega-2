package com.sisop.sisop.OS.Sdk.SharedQueue;

import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;

public class UcunixSharedQueuePop implements UcuCommand {
    @Override
    public String getCommandName() {
        return "sharedQueue.pop";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();

        if (value instanceof UcunixSharedQueue queue) {
            context.pushValue(queue.pop());
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
