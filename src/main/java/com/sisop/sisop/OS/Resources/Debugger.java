package com.sisop.sisop.OS.Resources;

import java.util.HashMap;

import com.sisop.sisop.OS.Process;
import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.OS.Scheduler;
import com.sisop.sisop.UcuLang.UcuLang;
import com.sisop.sisop.UcuLang.UcuLang.DebuggerCallback;
import com.sisop.sisop.UcuLang.UcuLang.StepMode;

public class Debugger {
    public static final ResourceId resourceId = new ResourceId();

    private final Scheduler scheduler;

    private final HashMap<ProcessId, DebuggerCallback> registeredCallbacks = new HashMap<>();

    public Debugger(Scheduler sch) {
        scheduler = sch;
    }

    public void attach(ProcessId pid) {
        if (!isAttachedTo(pid)) {
            var process = scheduler.getProcess(pid);
            assert process != null;
            var callback = getDebuggerCallback(scheduler.getProcess(pid));
            registeredCallbacks.put(pid, callback);
            process.getCode().addDebuggerCallback(callback);
            process.getCode().setStepMode(StepMode.Stop);
        }
    }

    public void dettach(ProcessId pid) {
        if (isAttachedTo(pid)) {
            var process = scheduler.getProcess(pid);
            assert process != null;
            registeredCallbacks.remove(pid);
            process.getCode().setStepMode(StepMode.Play);
            process.getCode().clearAllDebuggerCallbacks();
        }
    }

    public boolean isAttachedTo(ProcessId pid) {
        return registeredCallbacks.containsKey(pid);
    }

    public void play(ProcessId pid) {
        var process = scheduler.getProcess(pid);
        if (process != null) {
            if (isAttachedTo(pid)) {
                process.getCode().setStepMode(UcuLang.StepMode.Play);
            }
        }
    }

    public void stop(ProcessId pid) {
        var process = scheduler.getProcess(pid);
        if (process != null) {
            if (isAttachedTo(pid)) {
                process.getCode().setStepMode(UcuLang.StepMode.Stop);
            }
        }
    }

    public void step(ProcessId pid) {
        var process = scheduler.getProcess(pid);
        if (process != null) {
            if (isAttachedTo(pid)) {
                process.getCode().setStepMode(UcuLang.StepMode.PlayOne);
            }
        }
    }

    private DebuggerCallback getDebuggerCallback(Process process) {
        return (StepMode pre, StepMode post) -> {
            switch (post) {
                case Play, PlayOne -> {
                    scheduler.unblockProcess(process.getPid(), Debugger.resourceId);
                }
                case Stop -> {
                    scheduler.blockProcess(process.getPid(), Debugger.resourceId);
                }
            }
        };
    }
}
