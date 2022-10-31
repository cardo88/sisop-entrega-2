package com.sisop.sisop.OS;

import java.time.Duration;
import java.time.Instant;

public class ElapsedTime {
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
