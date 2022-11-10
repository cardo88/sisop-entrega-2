package uculang;
//
//import com.sisop.sisop.UcuLang.UcuCommand;
//import com.sisop.sisop.UcuLang.UcuContext;
//import com.sisop.sisop.UcuLang.Types.UcuAppendOp;

/**
 *
 */
public class UcuAppend implements UcuCommand {
    @Override
    public String getCommandName() {
        return "++";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var second = context.popValue();
        var first = context.popValue();

        if (first instanceof UcuAppendOp x) {
            var result = x.append(second);
            context.pushValue(result);
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
