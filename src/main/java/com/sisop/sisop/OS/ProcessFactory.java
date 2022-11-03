package com.sisop.sisop.OS;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.sisop.sisop.FileReader;
import com.sisop.sisop.OS.Resources.Debugger;
import com.sisop.sisop.OS.Resources.SleepTimer;
import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.UcuLang;
    
class Sleep implements UcuCommand {
    private final SleepTimer timer;
    private final ProcessId pid;

    public Sleep(SleepTimer timer, ProcessId pid) {
        this.timer = timer;
        this.pid = pid;
    }

    @Override
    public String getCommandName() {
        return "sleep";
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        long millis = value.asNumber().intValue();
        timer.addProcess(pid, millis);
        context.nextInstruction();
    }
}

class Print implements UcuCommand {
    private final Console console;

    public Print(Console console) {
        this.console = console;
    }

    @Override
    public String getCommandName() {
        return "print";
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        console.print(value.value.toString());
        context.nextInstruction();
    }
}

class PrintLn implements UcuCommand {
    private final Console console;

    public PrintLn(Console console) {
        this.console = console;
    }

    @Override
    public String getCommandName() {
        return "println";
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        console.print(value.value.toString() + "\n");
        context.nextInstruction();
    }
}

class Clear implements UcuCommand {
    private final Console console;

    public Clear(Console console) {
        this.console = console;
    }

    @Override
    public String getCommandName() {
        return "console.clear";
    }

    @Override
    public void execute(UcuContext context) {
        console.clear();
        context.nextInstruction();
    }
}

/**
 *
 */
class Breakpoint implements UcuCommand {
    private final ProcessId pid;
    private final Debugger debugger;

    public Breakpoint(ProcessId pid, Debugger debugger) {
        this.pid = pid;
        this.debugger = debugger;
    }

    @Override
    public String getCommandName() {
        return "breakpoint";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        context.nextInstruction();
        debugger.stop(pid);
    }
}

// class ConsoleWidth implements UcuCommand {
//     private final Console console;

//     public ConsoleWidth(Console console) {
//         this.console = console;
//     }

//     @Override
//     public String getCommandName() {
//         return "console.width";
//     }

//     @Override
//     public void execute(UcuContext context) {
//         context.pushValue(new UcuValue((double)console.getWidth()));
//         context.nextInstruction();
//     }
// }

// class ConsoleHeight implements UcuCommand {
//     private final Console console;

//     public ConsoleHeight(Console console) {
//         this.console = console;
//     }

//     @Override
//     public String getCommandName() {
//         return "console.height";
//     }

//     @Override
//     public void execute(UcuContext context) {
//         context.pushValue(new UcuValue((double)console.getHeight()));
//         context.nextInstruction();
//     }
// }

/**
 *
 */
public class ProcessFactory {
    private final SleepTimer sleepTimer;
    private final Debugger debugger;

    public ProcessFactory(SleepTimer sleepTimer, Debugger debugger) {
        this.sleepTimer = sleepTimer;
        this.debugger = debugger;
    }

    public Process fromSource(String name, String src, Console console) {
        var pid = new ProcessId();

        var ucuLang = new UcuLang();
        ucuLang.compile(src,
           new UcuCommand[] {
                new Sleep(sleepTimer, pid),
                new Print(console),
                new PrintLn(console),
                new Clear(console),
                new Breakpoint(pid, debugger),
            }
        );

        return new Process(name, pid, ucuLang);
    }

    public Process fromSourceFile(String path, Console console) throws FileNotFoundException, IOException {
        return fromSource(path, FileReader.readFile(path), console);
    }
}
