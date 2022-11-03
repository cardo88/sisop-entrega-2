package com.sisop.sisop.OS;

import java.util.HashMap;
import java.util.LinkedList;

import com.sisop.sisop.OS.Resources.ResourceId;

/**
 *
 */
public class Scheduler {
    private final HashMap<ProcessId, Process> allProcesses = new HashMap<>();

    private final LinkedList<Process> ready = new LinkedList<>();
    private final HashMap<ProcessId, Process> blocked = new HashMap<>();

    private ProcessRunner running = null;

    public Scheduler() { }

    public void addProcess(Process process) {
        if (process != null) {
            if (!allProcesses.containsKey(process.getPid())) {
                process.setState(Process.State.Ready);
                ready.add(process);
                allProcesses.put(process.getPid(), process);
            }
        }
    }

    public void finalizeProcess(ProcessId pid) {
        var process = allProcesses.get(pid);
        if (process != null) {
            switch (process.getState()) {
                case Running -> setNextRunningProcess();
                case Blocked -> blocked.remove(pid);
                case Finished -> { }
                case Ready -> ready.remove(process);
            }
            process.setState(Process.State.Finished);
            allProcesses.remove(process);
        }
    }

    public Process getProcess(ProcessId pid) {
        return allProcesses.get(pid);
    }

    public boolean unblockProcess(ProcessId pid, ResourceId blockedBy) {
        var process = blocked.get(pid);
        if (process != null) {
            process.removeBlockedBy(blockedBy);
            if (!process.isBlockedByAnything()) {
                blocked.remove(pid);
                process.setState(Process.State.Ready);
                ready.add(process);
                return true;
            }
        }
        return false;
    }

    public void blockProcess(ProcessId pid, ResourceId blockedBy) {
        var process = allProcesses.get(pid);
        if (process != null) {
            process.addBlockedBy(blockedBy);
            blocked.put(pid, process);

            switch (process.getState()) {
                case Blocked, Finished -> { }
                case Ready -> ready.remove(process);
                case Running -> setNextRunningProcess();
            }

            process.setState(Process.State.Blocked);
        }
    }

    public void step() {
        if (running == null) {
            setNextRunningProcess();
        }

        if (running != null) {
            switch (running.getCurrentState()) {
                case Running -> {
                    running.step();
                }
                case Paused -> {
                    running.process.setState(Process.State.Ready);
                    ready.add(running.process);
                    setNextRunningProcess();
                }
                case Finished -> {
                    running.process.setState(Process.State.Finished);
                    allProcesses.remove(running.process.getPid());
                    setNextRunningProcess();
                }
            }
        }
    }

    private void setNextRunningProcess() {
        var process = ready.pollFirst();
        if (process != null) {
            process.setState(Process.State.Running);
            running = new ProcessRunner(process, 10);
        } else {
            running = null;
        }
    }
}
