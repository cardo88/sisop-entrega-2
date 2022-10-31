package com.sisop.sisop.OS;

import java.util.HashMap;
import java.util.LinkedList;

import com.sisop.sisop.OS.Process.State;

/**
 *
 */
public class Scheduler {
    private final LinkedList<Process> ready = new LinkedList<>();
    private final HashMap<ProcessId, Process> blocked = new HashMap<>();
    private ProcessRunner running = null;

    private final int maxStepsPerProcess;

    private long waitTimeMs = 0;
    private ElapsedTime elapsedTime = new ElapsedTime(waitTimeMs);

    public Scheduler() {
        this(10);
    }

    public Scheduler(int maxStepsPerProcess) { 
        this.maxStepsPerProcess = maxStepsPerProcess;
    }

    public void addProcess(Process process) {
        if (process != null) {
            process.setState(Process.State.Ready);
            ready.add(process);
        }
    }

    public void setWaitTime(long ms) {
        waitTimeMs = ms;
        elapsedTime = new ElapsedTime(waitTimeMs);
    }

    public void unblockProcess(ProcessId pid) {
        var process = blocked.get(pid);
        if (process != null) {
            process.setState(Process.State.Ready);
            ready.add(process);
            blocked.remove(pid);
        }
    }

    public void blockProcess(ProcessId pid) {
        if (running != null) {
            if (running.process.getPid().equals(pid)) {
                running.process.setState(Process.State.Blocked);
                blocked.put(pid, running.process);
                setNextRunningProcess();
            }
        }
    }

    // public ProcessId getRunningProcess() {
    //     if (running != null) {
    //         return running.process.getPid();
    //     }
    //     return null;
    // }

    // public ProcessId[] getReadyProcessIds() {
    //     return ready.stream().map(x -> x.getPid()).toList().toArray(new ProcessId[0]);
    // }

    // public ProcessId[] getBlockedProcessIds() {
    //     return blocked.keySet().toArray(new ProcessId[0]);
    // }

    public void step() {
        if (!elapsedTime.isReady()) {
            return;
        }

        setWaitTime(waitTimeMs);

        if (running == null) {
            setNextRunningProcess();
        }

        if (running != null) {
            switch (running.getCurrentState()) {
                case Running -> running.step();
                case Paused -> {
                    running.process.setState(Process.State.Ready);
                    ready.add(running.process);
                    setNextRunningProcess();
                }
                case Finished -> {
                    running.process.setState(Process.State.Finished);
                    setNextRunningProcess();
                }
            }
        }
    }

    private void setNextRunningProcess() {
        var process = ready.pollFirst();
        if (process != null) {
            process.setState(Process.State.Running);
            running = new ProcessRunner(process, maxStepsPerProcess);
        } else {
            running = null;
        }
    }
}
