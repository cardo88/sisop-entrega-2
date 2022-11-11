package ucunix;

import uculang.UcuInterpreter.StepMode;
import java.util.HashSet;

/**
 *
 */
public class Debugger {
    public static final ResourceId resourceId = new ResourceId();
    
    private final Scheduler scheduler;
    
    private final HashSet<ProcessId> attachedTo;
    
    public Debugger(Scheduler scheduler) {
        this.scheduler = scheduler;
        this.attachedTo = new HashSet<>();
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    private boolean isAttachedTo(ProcessId pid) {
        return attachedTo.contains(pid);
    }
    
    public boolean attach(ProcessId pid) {
        var process = scheduler.getProcess(pid);
        if (process != null && !attachedTo.contains(pid)) {
            var interpreter = process.getInterpreter();
            interpreter.clearStepModeChangedActions();
            interpreter.addStepModeChangedAction((StepMode pre, StepMode post) -> {
                switch (post) {
                    case Pause -> {
                        scheduler.blockProcess(pid, Debugger.resourceId);
                    }
                    case Play, StepInto, StepOver -> {
                        scheduler.unblockProcess(pid, Debugger.resourceId);
                    }
                }
            });
            
            attachedTo.add(pid);
            
            return true;
        }
        
        return false;
    }
    
    public boolean dettach(ProcessId pid) {
        var process = scheduler.getProcess(pid);
        if (process != null && isAttachedTo(pid)) {
            attachedTo.remove(pid);
            var interpreter = process.getInterpreter();
            interpreter.clearStepModeChangedActions();
            interpreter.setStepMode(StepMode.Play);
            return true;
        }
        
        return false;
    }
    
    public boolean play(ProcessId pid) {
        var process = scheduler.getProcess(pid);
        if (process != null && isAttachedTo(pid)) {
            process.getInterpreter().setStepMode(StepMode.Play);
            return true;
        }
        
        return false;
    }
    
    public boolean pause(ProcessId pid) {
        var process = scheduler.getProcess(pid);
        if (process != null && isAttachedTo(pid)) {
            process.getInterpreter().setStepMode(StepMode.Pause);
            return true;
        }
        
        return false;
    }
    
    public boolean stepInto(ProcessId pid) {
        var process = scheduler.getProcess(pid);
        if (process != null && isAttachedTo(pid)) {
            process.getInterpreter().setStepMode(StepMode.StepInto);
            return true;
        }
        
        return false;
    }

    public boolean stepOver(ProcessId pid) {
        var process = scheduler.getProcess(pid);
        if (process != null && isAttachedTo(pid)) {
            process.getInterpreter().setStepMode(StepMode.StepOver);
            return true;
        }
        
        return false;
    }
}