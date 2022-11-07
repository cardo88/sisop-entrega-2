package com.sisop.sisop.UcuLang.Commands;

import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.Types.UcuAppendOp;

/**
 *
 */
public class UcuAppend implements UcuCommand {
    @Override
    public String getCommandName() {
        return "append";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var second = context.popValue();
        var first = context.popValue();

        if (first instanceof UcuAppendOp x) {
            context.pushValue(x.append(second));
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
