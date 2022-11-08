package com.sisop.sisop.OS.Sdk;

import java.util.Arrays;
import java.util.List;

import com.sisop.sisop.OS.Console;
import com.sisop.sisop.OS.Debugger;
import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.OS.Scheduler;
import com.sisop.sisop.OS.Sdk.Console.UcunixConsoleClear;
import com.sisop.sisop.OS.Sdk.Console.UcunixConsolePrint;
import com.sisop.sisop.OS.Sdk.Console.UcunixConsolePrintln;
import com.sisop.sisop.OS.Sdk.Debugger.UcunixDebuggerBreakpoint;
import com.sisop.sisop.OS.Sdk.Debugger.UcunixDebuggerStop;
import com.sisop.sisop.OS.Sdk.Process.UcunixProcessPid;
import com.sisop.sisop.OS.Sdk.Semaphore.UcunixSemaphoreCreate;
import com.sisop.sisop.OS.Sdk.Semaphore.UcunixSemaphoreP;
import com.sisop.sisop.OS.Sdk.Semaphore.UcunixSemaphoreV;
import com.sisop.sisop.OS.Sdk.SharedVariables.UcunixSharedCreate;
import com.sisop.sisop.OS.Sdk.Timer.UcunixTimer;
import com.sisop.sisop.OS.Sdk.Timer.UcunixTimerSleep;
import com.sisop.sisop.UcuLang.UcuCommand;

public class UcunixSdk {
    public static List<UcuCommand> getCommands(ProcessId pid, 
                                               Scheduler scheduler, 
                                               Console console, 
                                               Debugger debugger, 
                                               UcunixTimer timer) {
        return Arrays.asList(new UcuCommand[] {
            // Console
            new UcunixConsolePrint(console),
            new UcunixConsolePrintln(console),
            new UcunixConsoleClear(console),
            // Timer
            new UcunixTimerSleep(pid, timer),
            // Debugger
            new UcunixDebuggerBreakpoint(debugger, pid),
            new UcunixDebuggerStop(debugger, pid),
            // Semaphores
            new UcunixSemaphoreCreate(),
            new UcunixSemaphoreP(pid, scheduler),
            new UcunixSemaphoreV(scheduler),
            // Shared Variables
            new UcunixSharedCreate(pid),
            // Process
            new UcunixProcessPid(pid),
        });
    }
}
