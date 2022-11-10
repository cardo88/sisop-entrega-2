package ucunix;

/**
 *
 */
public interface Scheduler {
    void blockProcess(ProcessId pid, ResourceId blockedBy);
    
    void unblockProcess(ProcessId pid, ResourceId blockedBy);
    
    Process getProcess(ProcessId pid);
    
    void addProcess(Process process);

    void killProcess(ProcessId pid);
    
    void run(int instructionCountTimeout);
}
