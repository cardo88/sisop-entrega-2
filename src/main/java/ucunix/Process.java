package ucunix;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ucunix.sdk.SharedVariables;
import uculang.UcuInterpreter;
import uculang.UcuType;

/**
 *
 */
public class Process extends UcuType {
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

    private final ProcessId parent;
    private final User user;
    private State state;

    private Console console;
    private List<String> parameters;
    
    public Process(String name, ProcessId pid, User user, UcuInterpreter interpreter, ProcessId parent) {
        this.name = name;
        this.pid = pid;
        this.interpreter = interpreter;

        this.parent = parent;
        this.blockedBy = new HashSet<>();
        this.state = State.Ready;
        this.user = user;
        
        this.parameters = new LinkedList<>();

        setConsole(null);
    }
    
    public void setConsole(Console console) {
        if (console == null) {
            this.console = new NullConsole();
        } else {
            this.console = console;
        }
    }
    
    public User getUser() {
        return user;
    }

    public Console getConsole() {
        return console;
    }

    public ProcessId getParent() {
        return parent;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public List<String> getParameters() {
        return parameters;
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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof Process o) {
            return pid.equals(o.getPid());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return pid.hashCode();
    }

    @Override
    public String toString() {
        return "Process(" + pid.getId() + ")";
    }
}
