package ucunix.sdk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import uculang.CompilationError;
import ucunix.Ucunix;
import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.UcuString;
import ucunix.ProcessId;

public class ProcessRun implements UcuCommand {
    private final ProcessId pid;
    private final Ucunix ucunix;

    public ProcessRun(ProcessId pid, Ucunix ucunix) {
        this.pid = pid;
        this.ucunix = ucunix;
    }

    @Override
    public String getCommandName() {
        return "process.run";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        if (value instanceof UcuString path) {
            try {
                var scheduler = ucunix.getScheduler();
                
                var newProcess = ucunix.createProcess(
                    path.toString(), 
                    Files.readString(Path.of(path.toString())),
                    pid
                );
                newProcess.setConsole(scheduler.getProcess(pid).getConsole());
                context.pushValue(newProcess);
//                scheduler.blockProcess(pid, blockedBy);
            } catch (IOException | 
                     CompilationError ex) {
                Logger.getLogger(ProcessRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new RuntimeException("FIXME");
        }
//        context.pushValue(new UcuNumber(pid.getId()));
    }
}
