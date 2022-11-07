package com.sisop.sisop.OS.Sdk.Timer;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.OS.ResourceId;
import com.sisop.sisop.OS.Scheduler;

public class UcunixTimer {
    public static final ResourceId resourceId = new ResourceId();

    class Clock {
        public Instant start;
        public final long sleepForMs;

        public Clock(long sleepForMs) {
            start = Instant.now();
            this.sleepForMs = sleepForMs;
        }

        public boolean isReady() {
            return Duration.between(start, Instant.now()).toMillis() >= sleepForMs;
        }
    }

    private final Scheduler scheduler;
    private final Map<ProcessId, Clock> timers = new HashMap<>();

    public UcunixTimer(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void sleepProcess(ProcessId pid, long sleepForMs) {
        timers.put(pid, new Clock(sleepForMs));
        scheduler.block(pid, UcunixTimer.resourceId);
    }

    public void update() {
        LinkedList<ProcessId> toNotify = new LinkedList<>();

        for (Map.Entry<ProcessId, Clock> entry : timers.entrySet()) {
            if (entry.getValue().isReady()) {
                toNotify.add(entry.getKey());
            }
        }
        for (var process : toNotify) {
            scheduler.unblock(process, UcunixTimer.resourceId);
            timers.remove(process);
        }
    }
}
