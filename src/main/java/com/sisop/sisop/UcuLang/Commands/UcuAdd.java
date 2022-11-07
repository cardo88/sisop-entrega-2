package com.sisop.sisop.UcuLang.Commands;

import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.Types.UcuAddOp;

/**
 *
 */
public class UcuAdd implements UcuCommand {
    @Override
    public String getCommandName() {
        return "+";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var second = context.popValue();
        var first = context.popValue();

        if (first instanceof UcuAddOp x) {
            var result = x.add(second);
            context.pushValue(result);
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
