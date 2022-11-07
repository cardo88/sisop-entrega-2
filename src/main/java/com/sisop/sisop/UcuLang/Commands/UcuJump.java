package com.sisop.sisop.UcuLang.Commands;

import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.UcuInstruction;

/**
 *
 */
public class UcuJump implements UcuInstruction {
    public final String label;

    public UcuJump(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Jump: " + label;
    }

    @Override
    public void execute(UcuContext context) {
        context.jumpTo(label);
    }
}
