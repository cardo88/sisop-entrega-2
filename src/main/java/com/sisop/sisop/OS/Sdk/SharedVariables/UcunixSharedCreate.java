package com.sisop.sisop.OS.Sdk.SharedVariables;

import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.Exceptions.InvalidTypesRuntimeException;
import com.sisop.sisop.UcuLang.Types.UcuString;
import com.sisop.sisop.UcuLang.Types.UcuType;

public class UcunixSharedCreate implements UcuCommand {

    private final ProcessId pid;

    public UcunixSharedCreate(ProcessId pid) {
        this.pid = pid;
    }

    @Override
    public String getCommandName() {
        return "shared.get-or-create";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var seed = context.popValue();
        var name = context.popValue();

        if (name instanceof UcuString n) {
            context.pushValue(UcunixSharedVariables.getOrCreate(pid, name.toString(), seed));
        } else {
            throw new InvalidTypesRuntimeException(
                getCommandName(), 
                new String[][] { 
                    { UcuString.class.getName() },
                    { UcuType.class.getName() },
                }, 
                new String[][] { 
                    { name.getClass().getName() },
                    { seed.getClass().getName() },
                }
            );
        }
    }
}
