package ucunix.sdk;

import java.io.IOException;
import ucunix.ProcessId;
import ucunix.Scheduler;
import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.UcuString;
import ucunix.Filesystem;
import ucunix.Filesystem.UcunixFile;

public class FileGetContent implements UcuCommand {
    private final ProcessId pid;
    private final Scheduler scheduler;

    public FileGetContent(ProcessId pid, Scheduler scheduler, Filesystem filesystem) {
        this.pid = pid;
        this.scheduler = scheduler;
    }

    @Override
    public String getCommandName() {
        return "file.get-content";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        if (value instanceof UcunixFile file) {
            var process = scheduler.getProcess(pid);
            if (process != null) {
                var user = process.getUser();
                if (file.getPermissions().canRead(user)) {
                    try {
                        context.pushValue(new UcuString(file.getContent()));
                        context.pushValue(new UcuString("OK"));
                    } catch (IOException ex) {
                        context.pushValue(new UcuString("ERROR"));
                    }
                } else {
                    context.pushValue(new UcuString("ERROR"));
                }
            }
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
