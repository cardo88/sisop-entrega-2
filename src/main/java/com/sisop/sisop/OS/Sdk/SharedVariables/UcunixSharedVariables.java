package com.sisop.sisop.OS.Sdk.SharedVariables;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.UcuLang.Exceptions.InvalidTypesRuntimeException;
import com.sisop.sisop.UcuLang.Types.UcuList;
import com.sisop.sisop.UcuLang.Types.UcuType;

public class UcunixSharedVariables {
    private static Map<String, UcuType> variables = new HashMap<>();
    private static Map<String, Set<ProcessId>> referencedBy = new HashMap<>();

    public static UcuType getOrCreate(ProcessId pid, String name, UcuType value) {
        // if (!(value instanceof UcuList)) {
        //     throw new InvalidTypesRuntimeException(
        //         "SharedVariables.getOrCreate", 
        //         new String[][] {
        //             { }
        //         }, null)
        // }

        if (!variables.containsKey(name)) {
            variables.put(name, value);
        }

        if (!referencedBy.containsKey(name)) {
            referencedBy.put(name, new HashSet<>());
        }

        referencedBy.get(name).add(pid);

        return variables.get(name);
    }

    public static void releaseProcess(ProcessId pid) {
        for (Map.Entry<String, Set<ProcessId>> entry : referencedBy.entrySet()) {
            entry.getValue().remove(pid);
            if (entry.getValue().size() == 0) {
                variables.remove(entry.getKey());
            }
        }
    }
}
