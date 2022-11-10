package uculang;

//import com.sisop.sisop.UcuLang.UcuCommand;
//import com.sisop.sisop.UcuLang.UcuContext;
//import com.sisop.sisop.UcuLang.Types.UcuGetOp;

/**
 *
 */
public class UcuGet implements UcuCommand {
    @Override
    public String getCommandName() {
        return "get";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var index = context.popValue();
        var value = context.popValue();

        if (value instanceof UcuGetOp x) {
            context.pushValue(x.get(index));
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
