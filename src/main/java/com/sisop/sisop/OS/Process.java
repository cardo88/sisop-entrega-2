package com.sisop.sisop.OS;

import java.util.HashSet;
import java.util.Set;

import com.sisop.sisop.OS.Sdk.SharedVariables.UcunixSharedVariables;
import com.sisop.sisop.UcuLang.UcuInterpreter;

/**
 *
 */
public class Process {
    public enum State {
        Ready,
        Running,
        Blocked,
        Finished,
    }

    private final String name;
    private final ProcessId pid;

    private final UcuInterpreter interpreter;
    private final Set<ResourceId> blockedBy;

    private State state;

    public Process(String name, ProcessId pid, UcuInterpreter interpreter) {
        this.pid = pid;
        this.interpreter = interpreter;
        this.name = name;
        this.state = State.Ready;
        this.blockedBy = new HashSet<>();
    }

    public void onKill() {
        UcunixSharedVariables.releaseProcess(pid);
    }

    /**
     * Obtener pid de este proceso
     * 
     * @return El ProcessId
     */
    public ProcessId getPid() {
        return pid;
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    public UcuInterpreter getInterpreter() {
        return interpreter;
    }

    public boolean isBlockedByAnything() {
        return !blockedBy.isEmpty();
    }

    public void setState(State s) {
        state = s;
    }

    public State getState() {
        return state;
    }

    public void addBlockedBy(ResourceId id) {
        blockedBy.add(id);
    }

    public void removeBlockedBy(ResourceId id) {
        blockedBy.remove(id);
    }
}
