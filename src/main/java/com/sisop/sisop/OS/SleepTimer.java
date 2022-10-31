package com.sisop.sisop.OS;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 */
public class SleepTimer {
    private final HashMap<ProcessId, ElapsedTime> timers = new HashMap<>();

    private final Scheduler scheduler;

    public SleepTimer(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void addProcess(ProcessId pid, long waitForMillis) {
        timers.put(pid, new ElapsedTime(waitForMillis));
        scheduler.blockProcess(pid);
    }

    public void step() {
        LinkedList<ProcessId> toNotify = new LinkedList<>();

        for (Map.Entry<ProcessId, ElapsedTime> entry : timers.entrySet()) {
            if (entry.getValue().isReady()) {
                toNotify.add(entry.getKey());
            }
        }

        for (var process : toNotify) {
            timers.remove(process);
            scheduler.unblockProcess(process);
        }
    }
}
