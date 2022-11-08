package com.sisop.sisop.UcuLang.Commands;

import com.sisop.sisop.UcuLang.Types.UcuCopyOp;
import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;

/**
 *
 */
public class UcuCopy implements UcuCommand {
    @Override
    public String getCommandName() {
        return "copy";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.peekValue();
        if (value instanceof UcuCopyOp x) {
            context.pushValue(x.copy());
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
