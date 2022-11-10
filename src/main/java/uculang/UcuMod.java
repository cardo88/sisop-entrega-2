package uculang;

//import com.sisop.sisop.UcuLang.UcuCommand;
//import com.sisop.sisop.UcuLang.UcuContext;
//import com.sisop.sisop.UcuLang.Types.UcuModOp;

/**
 *
 */
public class UcuMod implements UcuCommand {
    @Override
    public String getCommandName() {
        return "%";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var second = context.popValue();
        var first = context.popValue();

        if (first instanceof UcuModOp x) {
            context.pushValue(x.mod(second));
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
