package com.sisop.sisop.OS;

/**
 *
 */
public interface Scheduler {
    void block(ProcessId pid, ResourceId blockedBy);
    
    void unblock(ProcessId pid, ResourceId blockedBy);
    
    Process get(ProcessId pid);
    
    void add(Process process);

    void kill(ProcessId pid);
    
    void run(int instructionCountTimeout);
    
    default void run() {
        run(10);
    }   
}
