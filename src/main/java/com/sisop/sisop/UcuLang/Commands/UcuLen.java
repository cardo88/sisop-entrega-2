package com.sisop.sisop.UcuLang.Commands;

import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.Types.UcuNumber;
import com.sisop.sisop.UcuLang.Types.UcuLengthOp;

/**
 *
 */
public class UcuLen implements UcuCommand {
    @Override
    public String getCommandName() {
        return "len";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();

        if (value instanceof UcuLengthOp x) {
            context.pushValue(new UcuNumber(x.length()));
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
