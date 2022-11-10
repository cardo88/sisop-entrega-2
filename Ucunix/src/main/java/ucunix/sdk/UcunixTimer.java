package ucunix.sdk;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import ucunix.ProcessId;
import ucunix.ResourceId;
import ucunix.Scheduler;

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
        scheduler.blockProcess(pid, UcunixTimer.resourceId);
    }

    public void update() {
        var toDelete = new LinkedList<ProcessId>();

        timers.entrySet()
              .forEach(x -> {
                if (x.getValue().isReady()) {
                    scheduler.unblockProcess(x.getKey(), resourceId);
                    toDelete.add(x.getKey());
                }
              });

        toDelete.forEach(x -> timers.remove(x));
    }
}
