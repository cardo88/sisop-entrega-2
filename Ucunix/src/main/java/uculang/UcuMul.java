package uculang;

//import com.sisop.sisop.UcuLang.UcuCommand;
//import com.sisop.sisop.UcuLang.UcuContext;
//import com.sisop.sisop.UcuLang.Types.UcuMulOp;

/**
 *
 */
public class UcuMul implements UcuCommand {
    @Override
    public String getCommandName() {
        return "*";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var second = context.popValue();
        var first = context.popValue();

        if (first instanceof UcuMulOp x) {
            context.pushValue(x.mul(second));
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
