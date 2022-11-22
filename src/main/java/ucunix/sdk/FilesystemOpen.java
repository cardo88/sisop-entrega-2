package ucunix.sdk;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import ucunix.ProcessId;
import ucunix.Scheduler;
import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.UcuList;
import uculang.UcuString;
import ucunix.Filesystem;

public class FilesystemOpen implements UcuCommand {
    private final ProcessId pid;
    private final Scheduler scheduler;
    private final Filesystem filesystem;

    public FilesystemOpen(ProcessId pid, Scheduler scheduler, Filesystem filesystem) {
        this.pid = pid;
        this.scheduler = scheduler;
        this.filesystem = filesystem;
    }

    @Override
    public String getCommandName() {
        return "filesystem.open";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        if (value instanceof UcuString name) {
            var process = scheduler.getProcess(pid);
            if (process != null) {
                var user = process.getUser();
                try {
                    context.pushValue(filesystem.open(user, name.toString()));
                    context.pushValue(new UcuString("OK"));
                } catch (IOException ex) {
                    context.pushValue(new UcuString("ERROR"));
                }
            }
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
