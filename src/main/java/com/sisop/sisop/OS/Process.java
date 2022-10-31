package com.sisop.sisop.OS;

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

    public Process(String name, ProcessId pid, UcuLang code) {
        this.pid = pid;
        this.code = code;
        this.name = name;
        this.state = State.Ready;
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

    public boolean step() {
        return code.next();
    }
}
