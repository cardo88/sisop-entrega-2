package ucunix.sdk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import uculang.DuplicatedLabel;
import uculang.DuplicatedLocalLabel;
import uculang.LocalLabelWithoutParent;
import uculang.LocalVariableWithoutParent;
import ucunix.Ucunix;
import uculang.UcuCommand;
import uculang.UcuContext;
import uculang.UcuString;
import uculang.UnknownCommand;
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
                var p = ucunix.createProcess(path.toString(), Files.readString(Path.of(path.toString())));
                p.setConsole(ucunix.getScheduler().getProcess(pid).getConsole());
            } catch (IOException ex) {
                Logger.getLogger(ProcessRun.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownCommand ex) {
                Logger.getLogger(ProcessRun.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LocalLabelWithoutParent ex) {
                Logger.getLogger(ProcessRun.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LocalVariableWithoutParent ex) {
                Logger.getLogger(ProcessRun.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DuplicatedLabel ex) {
                Logger.getLogger(ProcessRun.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DuplicatedLocalLabel ex) {
                Logger.getLogger(ProcessRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new RuntimeException("FIXME");
        }
//        context.pushValue(new UcuNumber(pid.getId()));
    }
}
