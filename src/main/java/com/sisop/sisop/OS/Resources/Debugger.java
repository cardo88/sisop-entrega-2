package com.sisop.sisop.OS.Resources;

import com.sisop.sisop.OS.Process;
import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.OS.Scheduler;
import com.sisop.sisop.UcuLang.UcuLang;
import com.sisop.sisop.UcuLang.UcuLang.DebuggerCallback;
import com.sisop.sisop.UcuLang.UcuLang.StepMode;

public class Debugger {
    public static final ResourceId resourceId = new ResourceId();

    private final Scheduler scheduler;

    public Debugger(Scheduler sch) {
        scheduler = sch;
    }

    public void play(ProcessId pid) {
        var process = scheduler.getProcess(pid);
        if (process != null) {
            process.getCode().setDebuggerCallback(getDebuggerCallback(process));
            process.getCode().setStepMode(UcuLang.StepMode.Play);
        }
    }

    public void stop(ProcessId pid) {
        var process = scheduler.getProcess(pid);
        if (process != null) {
            process.getCode().setDebuggerCallback(getDebuggerCallback(process));
            process.getCode().setStepMode(UcuLang.StepMode.Stop);
        }
    }

    public void step(ProcessId pid) {
        var process = scheduler.getProcess(pid);
        if (process != null) {
            process.getCode().setDebuggerCallback(getDebuggerCallback(process));
            process.getCode().setStepMode(UcuLang.StepMode.PlayOne);
        }
    }

    private DebuggerCallback getDebuggerCallback(Process process) {
        return (StepMode pre, StepMode post) -> {
            switch (post) {
                case Play, PlayOne -> scheduler.unblockProcess(process.getPid(), Debugger.resourceId);
                case Stop -> scheduler.blockProcess(process.getPid(), Debugger.resourceId);
            }
        };
    }
}
