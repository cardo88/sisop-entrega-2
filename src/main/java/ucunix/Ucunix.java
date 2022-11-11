package ucunix;

import java.util.Arrays;
import java.util.List;

import uculang.UcuLang;
import uculang.UnknownCommand;
import uculang.DuplicatedLabel;
import uculang.DuplicatedLocalLabel;
import uculang.LocalLabelWithoutParent;
import uculang.LocalVariableWithoutParent;
import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.UcuInterpreter;
import ucunix.sdk.*;

/**
 *
 */
public class Ucunix {
    private final Scheduler scheduler;
    private final UcunixTimer timer;

    private int instructionCountTimeout;
    
    public Ucunix(Scheduler scheduler) {
        this.scheduler = scheduler;
        this.timer = new UcunixTimer(scheduler);

        this.instructionCountTimeout = 10;
    }
    
    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setInstructionCountTimeout(int timeout) {
        this.instructionCountTimeout = timeout > 0 ? timeout : 1;
    }

    public int getInstructionCountTimeout() {
        return instructionCountTimeout;
    }
    
    public Process createProcess(String processName, String sourceCode, ProcessId parent) 
        throws UnknownCommand, 
               LocalLabelWithoutParent, 
               LocalVariableWithoutParent, 
               DuplicatedLabel, 
               DuplicatedLocalLabel 
    {
        var pid = new ProcessId();
        var ucuProgram = UcuLang.compile(sourceCode, getCommands(pid));
        var context = new UcuContext(ucuProgram);
        var process = new Process(processName, pid, new UcuInterpreter(context), parent);

        scheduler.addProcess(process);

        return process;
    }
    
    public void update() {
        scheduler.run(instructionCountTimeout);
        timer.update();
    }

    private List<UcuCommand> getCommands(ProcessId pid) {
        return Arrays.asList(new UcuCommand[] {
            // Console
            new ConsolePrint(pid, scheduler),
            new ConsolePrintln(pid, scheduler),
            new ConsoleClear(pid, scheduler),
            new ConsoleRead(pid, scheduler),
            // Timer
            new UcunixTimerSleep(pid, timer),
            // Debugger
            // new DebuggerBreakpoint(pid, debugger),
            // new DebuggerStop(pid, debugger),
            // Semaphores
            new SemaphoreCreate(pid),
            new SemaphoreP(pid, scheduler),
            new SemaphoreV(scheduler),
            // Shared Variables
            new SharedCreate(pid),
            // Process
            new ProcessSelf(pid, scheduler),
            new ProcessGetPid(),
            new ProcessRun(pid, this),
            new ProcessGetState(),
            new ProcessKill(scheduler),
            new ProcessGetParameters(),
        });
    }
}
