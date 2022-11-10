package uculang;

//import com.sisop.sisop.UcuLang.UcuCommand;
//import com.sisop.sisop.UcuLang.UcuContext;

/**
 *
 */
public class UcuRot implements UcuCommand {
    @Override
    public String getCommandName() {
        return "rot";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var c = context.popValue();
        var b = context.popValue();
        var a = context.popValue();
        context.pushValue(b);
        context.pushValue(c);
        context.pushValue(a);
    }
}
