package ucunix.sdk;

import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.UcuString;
import ucunix.Filesystem.UcunixFile;

public class FileGetName implements UcuCommand {
    @Override
    public String getCommandName() {
        return "file.get-name";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        if (value instanceof UcunixFile file) {
            context.pushValue(new UcuString(file.getName()));
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
