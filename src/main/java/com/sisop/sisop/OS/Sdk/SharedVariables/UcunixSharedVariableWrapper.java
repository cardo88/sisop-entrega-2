package com.sisop.sisop.OS.Sdk.SharedVariables;

import java.util.HashSet;
import java.util.Set;

import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.UcuLang.Types.UcuType;

public class UcunixSharedVariableWrapper {
    public final UcuType value;
    public final Set<ProcessId> referencedBy;
    
    public UcunixSharedVariableWrapper(UcuType value) {
        this.value = value;
        this.referencedBy = new HashSet<>();
    }
}
