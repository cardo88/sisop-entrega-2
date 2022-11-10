package ucunix;

import java.util.HashSet;
import java.util.Set;

import ucunix.sdk.SharedVariables;
import uculang.UcuInterpreter;

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
    private Console console;

    public Process(String name, ProcessId pid, UcuInterpreter interpreter) {
        this.name = name;
        this.pid = pid;
        this.interpreter = interpreter;

        this.blockedBy = new HashSet<>();
        this.state = State.Ready;

        setConsole(null);
    }
    
    public void setConsole(Console console) {
        if (console == null) {
            this.console = new NullConsole();
        } else {
            this.console = console;
        }
    }
    
    public Console getConsole() {
        return console;
    }

    public void onKill() {
        SharedVariables.releaseProcess(pid);
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
