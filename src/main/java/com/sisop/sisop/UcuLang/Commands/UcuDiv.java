package com.sisop.sisop.UcuLang.Commands;

import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.Types.UcuDivOp;

/**
 *
 */
public class UcuDiv implements UcuCommand {
    @Override
    public String getCommandName() {
        return "/";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var second = context.popValue();
        var first = context.popValue();

        if (first instanceof UcuDivOp x) {
            context.pushValue(x.div(second));
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
