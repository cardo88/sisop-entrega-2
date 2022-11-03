package com.sisop.sisop.OS.Resources;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.OS.Scheduler;

/**
 *
 */
public class SleepTimer {
    class ElapsedTime {
        public Instant start;
        public final long waitForMillis;

        public ElapsedTime(long waitForMillis) {
            start = Instant.now();
            this.waitForMillis = waitForMillis;
        }

        public boolean isReady() {
            return Duration.between(start, Instant.now()).toMillis() >= waitForMillis;
        }
    }

    public static final ResourceId resourceId = new ResourceId();

    private final HashMap<ProcessId, ElapsedTime> timers = new HashMap<>();

    private final Scheduler scheduler;

    public SleepTimer(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void addProcess(ProcessId pid, long waitForMillis) {
        timers.put(pid, new ElapsedTime(waitForMillis));
        scheduler.blockProcess(pid, SleepTimer.resourceId);
    }

    public void step() {
        LinkedList<ProcessId> toNotify = new LinkedList<>();

        for (Map.Entry<ProcessId, ElapsedTime> entry : timers.entrySet()) {
            if (entry.getValue().isReady()) {
                toNotify.add(entry.getKey());
            }
        }

        for (var process : toNotify) {
            scheduler.unblockProcess(process, resourceId);
            timers.remove(process);
        }
    }
}
