package ucunix.sdk;

import java.util.LinkedList;
import java.util.List;

import ucunix.ProcessId;
import ucunix.ResourceId;
import ucunix.Scheduler;
import uculang.UcuType;

/**
 *
 */
public class Semaphore extends UcuType {
    private final ResourceId resourceId;
    private final String name;
    private final LinkedList<ProcessId> blockedProcesses;

    private int capacity;

    public Semaphore(String name, int capacity) {
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
            if (scheduler.getProcess(pid) != null) {
                scheduler.blockProcess(pid, resourceId);
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
                scheduler.unblockProcess(pid, resourceId);
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
