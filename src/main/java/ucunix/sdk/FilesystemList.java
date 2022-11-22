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

public class FilesystemList implements UcuCommand {
    private final ProcessId pid;
    private final Scheduler scheduler;
    private final Filesystem filesystem;

    public FilesystemList(ProcessId pid, Scheduler scheduler, Filesystem filesystem) {
        this.pid = pid;
        this.scheduler = scheduler;
        this.filesystem = filesystem;
    }

    @Override
    public String getCommandName() {
        return "filesystem.list";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var process = scheduler.getProcess(pid);
        if (process != null) {
            var user = process.getUser();
            try {
                var list = new UcuList();
                filesystem.listFiles(user)
                            .stream()
                            .forEach(x -> {
                                list.append(x);
                            });
                context.pushValue(list);
            } catch (IOException ex) {
                Logger.getLogger(FilesystemList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
