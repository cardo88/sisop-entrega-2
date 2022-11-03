package com.sisop.sisop.OS;

import com.sisop.sisop.OS.Resources.ResourceId;
import java.util.HashSet;
import java.util.Set;

import com.sisop.sisop.UcuLang.UcuLang;

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

    private State state;
    private UcuLang code;

    private HashSet<ResourceId> blockedBy;

    public Process(String name, ProcessId pid, UcuLang code) {
        this.pid = pid;
        this.code = code;
        this.name = name;
        this.state = State.Ready;
        this.blockedBy = new HashSet<>();
    }

    public ProcessId getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public void setState(State s) {
        state = s;
    }

    public State getState() {
        return state;
    }

    public UcuLang getCode() {
        return code;
    }

    public boolean step() {
        return code.next();
    }

    public void addBlockedBy(ResourceId id) {
        blockedBy.add(id);
    }

    public void removeBlockedBy(ResourceId id) {
        blockedBy.remove(id);
    }

    public boolean isBlockedBy(ResourceId id) {
        return blockedBy.contains(id);
    }

    public boolean isBlockedByAnything() {
        return blockedBy.size() != 0;
    }

    public Set<ResourceId> getBlockedBy() {
        return blockedBy;
    }
}
