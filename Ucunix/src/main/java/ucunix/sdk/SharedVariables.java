package ucunix.sdk;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;

import ucunix.ProcessId;
import uculang.UcuType;

public class SharedVariables {

    public static class Wrapper {
        public final UcuType value;
        public final Set<ProcessId> referencedBy;
        
        public Wrapper(UcuType value) {
            this.value = value;
            this.referencedBy = new HashSet<>();
        }
    }

    private static final Map<String, Wrapper> variables = new HashMap<>();

    public static UcuType getOrCreate(ProcessId pid, String name, UcuType value) {
        if (!variables.containsKey(name)) {
            variables.put(name, new Wrapper(value));
        }
        
        var wrapper = variables.get(name);
        wrapper.referencedBy.add(pid);

        return wrapper.value;
    }

    public static Map<String, Wrapper> getAll() {
        return variables;
    }

    public static void releaseProcess(ProcessId pid) {
        LinkedList<String> toRemove = new LinkedList<>();

        for (Map.Entry<String, Wrapper> entry : variables.entrySet()) {
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
