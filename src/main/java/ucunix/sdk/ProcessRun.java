package ucunix.sdk;

import java.nio.file.Files;
import java.nio.file.Path;
import ucunix.Ucunix;
import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.UcuList;
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
    public void execute(UcuContext context) throws Exception {
        var parametersValue = context.popValue();
        var pathValue = context.popValue();
        if (pathValue instanceof UcuString path
            && parametersValue instanceof UcuList parameters) {

            var scheduler = ucunix.getScheduler();
            
            var newProcess = ucunix.createProcess(
                path.toString(), 
                Files.readString(Path.of(path.toString())),
                pid
            );
            newProcess.setConsole(scheduler.getProcess(pid).getConsole());
            newProcess.setParameters(parameters.getList()
                                                .stream()
                                                .map(x -> x.toString())
                                                .toList());

            context.pushValue(newProcess);
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
