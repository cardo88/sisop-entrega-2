package com.sisop.sisop.OS.Sdk.Semaphore;

import java.util.LinkedList;
import java.util.List;

import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.OS.ResourceId;
import com.sisop.sisop.OS.Scheduler;
import com.sisop.sisop.UcuLang.Types.UcuType;

/**
 *
 */
public class UcunixSemaphore extends UcuType {
    private final ResourceId resourceId;
    private final String name;
    private final LinkedList<ProcessId> blockedProcesses;

    private int capacity;

    public UcunixSemaphore(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.resourceId = new ResourceId();
        this.blockedProcesses = new LinkedList<>();
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
        return "UcunixSemaphore(" + name + ")";
    }

    @Override
    public boolean equals(Object other) {
        return this == other;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
