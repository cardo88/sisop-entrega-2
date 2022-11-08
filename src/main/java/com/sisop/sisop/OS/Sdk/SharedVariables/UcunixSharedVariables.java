package com.sisop.sisop.OS.Sdk.SharedVariables;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.UcuLang.Types.UcuType;

public class UcunixSharedVariables {
    private static final Map<String, UcunixSharedVariableWrapper> variables = new HashMap<>();

    public static UcuType getOrCreate(ProcessId pid, String name, UcuType value) {
        if (!variables.containsKey(name)) {
            variables.put(name, new UcunixSharedVariableWrapper(value));
        }
        
        var wrapper = variables.get(name);
        wrapper.referencedBy.add(pid);

        return wrapper.value;
    }

    public static Map<String, UcunixSharedVariableWrapper> getAll() {
        return variables;
    }

    public static void releaseProcess(ProcessId pid) {
        LinkedList<String> toRemove = new LinkedList<>();

        for (Map.Entry<String, UcunixSharedVariableWrapper> entry : variables.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();

            value.referencedBy.remove(pid);
            if (value.referencedBy.isEmpty()) {
                toRemove.add(key);
            }
        }

        for (var name : toRemove) {
            variables.remove(name);
        }
    }
}
