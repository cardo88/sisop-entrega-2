package com.sisop.sisop.OS.Sdk.SharedVariables;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.UcuLang.Types.UcuType;

public class UcunixSharedVariables {
    private static Map<String, UcuType> variables = new HashMap<>();
    private static Map<String, Set<ProcessId>> referencedBy = new HashMap<>();

    public static UcuType getOrCreate(ProcessId pid, String name, UcuType value) {
        if (!variables.containsKey(name)) {
            variables.put(name, value);
        }

        if (!referencedBy.containsKey(name)) {
            referencedBy.put(name, new HashSet<>());
        }

        referencedBy.get(name).add(pid);

        return variables.get(name);
    }

    public static Map<String, UcuType> getAll() {
        return variables;
    }

    public static void releaseProcess(ProcessId pid) {
        LinkedList<String> toRemove = new LinkedList<>();

        for (Map.Entry<String, Set<ProcessId>> entry : referencedBy.entrySet()) {
            var value = entry.getValue();
            var key = entry.getKey();

            value.remove(pid);
            if (value.size() == 0) {
                variables.remove(key);
                toRemove.add(key);
            }
        }

        for (var name : toRemove) {
            referencedBy.remove(name);
        }
    }
}
