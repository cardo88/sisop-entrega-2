package com.sisop.sisop.OS.Sdk.Semaphore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.OS.ResourceId;
import com.sisop.sisop.OS.Scheduler;
import com.sisop.sisop.UcuLang.Types.UcuType;

/**
 * Semáforo
 *
 */
public class UcunixSemaphore extends UcuType {
    private static final Map<String, UcunixSemaphore> semaphores = new HashMap<>();

    private final ResourceId resourceId;
    private final String name;
    private final LinkedList<ProcessId> blockedProcesses;

    private int capacity;

    public static UcunixSemaphore create(String name, int capacity) {
        if (!semaphores.containsKey(name)) {
            semaphores.put(name, new UcunixSemaphore(name, capacity));
        }
        return semaphores.get(name);
    }

    private UcunixSemaphore(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.resourceId = new ResourceId();
        this.blockedProcesses = new LinkedList<>();
    }

    public static List<UcunixSemaphore> getAll() {
        return semaphores.values().stream().toList();
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<ProcessId> getBlockedPids() {
        return blockedProcesses;
    }

    public void P(ProcessId pid, Scheduler scheduler) {
        if (capacity > 0) {
            capacity--;
        } else {
            if (scheduler.get(pid) != null) {
                scheduler.block(pid, resourceId);
                blockedProcesses.add(pid);
            }
        }
    }

    public void V(Scheduler scheduler) {
        capacity++;
        if (capacity > 0) {
            var pid = blockedProcesses.pollFirst();
            if (pid != null) {
                capacity--;
                scheduler.unblock(pid, resourceId);
            }
        }
    }

    @Override
    public String toString() {
        return "Semaphore: " + name;
    }

    @Override
    public boolean equals(Object other) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return 0;
    }
}
