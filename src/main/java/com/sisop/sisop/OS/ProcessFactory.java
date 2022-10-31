package com.sisop.sisop.OS;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.sisop.sisop.FileReader;
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
        long millis = (long)((double)value.value);
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

/**
 *
 */
public class ProcessFactory {
    private final SleepTimer sleepTimer;

    public ProcessFactory(SleepTimer sleepTimer) {
        this.sleepTimer = sleepTimer;
    }

    public Process fromSource(String name, String src, Console console) {
        var pid = new ProcessId();

        var ucuLang = new UcuLang();
        ucuLang.compile(
            src,
            new UcuCommand[] {
                new Sleep(sleepTimer, pid),
                new Print(console),
                new PrintLn(console),
            }
        );

        return new Process(name, pid, ucuLang);
    }

    public Process fromSourceFile(String path, Console console) throws FileNotFoundException, IOException {
        return fromSource(path, FileReader.readFile(path), console);
    }
}