package ucunix.sdk;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ucunix.ProcessId;
import ucunix.Scheduler;
import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.UcuList;
import uculang.UcuString;
import ucunix.Filesystem;
import ucunix.Filesystem.UcunixFile;

public class FileGetMeta implements UcuCommand {
    @Override
    public String getCommandName() {
        return "file.get-meta";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        if (value instanceof UcunixFile file) {
            context.pushValue(new UcuString(file.getPermissions().toString()));
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
