package com.sisop.sisop.UcuLang.Commands;

import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.UcuInstruction;
import com.sisop.sisop.UcuLang.Types.UcuCopyOp;

/**
 *
 */
public class UcuPushValue implements UcuInstruction {
    private final UcuCopyOp value;

    public UcuPushValue(UcuCopyOp value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Push: " + value.toString();
    }

    @Override
    public void execute(UcuContext context) {
        context.pushValue(value.copy());
    }
}
