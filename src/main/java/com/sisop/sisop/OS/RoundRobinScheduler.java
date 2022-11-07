package com.sisop.sisop.OS;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 */
public class RoundRobinScheduler implements Scheduler {
    private final Map<ProcessId, Process> allProcesses;
    private final LinkedList<Process> ready;
    private final Map<ProcessId, Process> blocked;

    private Process running;

    public RoundRobinScheduler() { 
        this.allProcesses = new HashMap<>();
        this.ready = new LinkedList<>();
        this.blocked = new HashMap<>();
        this.running = null;
    }

    @Override
    public Process get(ProcessId pid) {
        return allProcesses.get(pid);
    }

    @Override
    public void add(Process process) {
        if (!allProcesses.containsKey(process.getPid())) {
            process.setState(Process.State.Ready);
            ready.add(process);
            allProcesses.put(process.getPid(), process);
        }
    }

    @Override
    public void kill(ProcessId pid) {
        var process = allProcesses.get(pid);
        if (process != null) {
            switch (process.getState()) {
                case Running -> setNextRunningProcess();
                case Blocked -> blocked.remove(pid);
                case Finished -> { }
                case Ready -> ready.remove(process);
            }
            process.setState(Process.State.Finished);
            process.onKill();
            allProcesses.remove(pid);
        }
    }

    @Override
    public void unblock(ProcessId pid, ResourceId blockedBy) {
        var process = blocked.get(pid);
        if (process != null) {
            process.removeBlockedBy(blockedBy);
            if (!process.isBlockedByAnything()) {
                blocked.remove(pid);
                process.setState(Process.State.Ready);
                ready.add(process);
            }
        }
    }

    @Override
    public void block(ProcessId pid, ResourceId blockedBy) {
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

    @Override
    public void run(int instructionCountTimeout) {
        if (running == null) {
            setNextRunningProcess();
        }

        if (running != null) {
            var interpreter = running.getInterpreter();
            var process = running;
            for (int i = 0; i < instructionCountTimeout; i++) {
                if (process.getState() == Process.State.Running) {
                    boolean stillGoing = interpreter.run();
                    if (!stillGoing) {
                        kill(process.getPid());
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }

    private void setNextRunningProcess() {
        var process = ready.pollFirst();
        if (process != null) {
            process.setState(Process.State.Running);
            running = process;
        } else {
            running = null;
        }
    }
}
