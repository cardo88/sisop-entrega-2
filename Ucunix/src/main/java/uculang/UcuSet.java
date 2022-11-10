package uculang;

//import com.sisop.sisop.UcuLang.UcuCommand;
//import com.sisop.sisop.UcuLang.UcuContext;
//import com.sisop.sisop.UcuLang.Types.UcuSetOp;

/**
 *
 */
public class UcuSet implements UcuCommand {
    @Override
    public String getCommandName() {
        return "set";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        var key = context.popValue();
        var list = context.popValue();

        if (list instanceof UcuSetOp x) {
            x.set(key, value);
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
