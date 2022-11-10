package uculang;

//import com.sisop.sisop.UcuLang.UcuCommand;
//import com.sisop.sisop.UcuLang.UcuContext;

/**
 *
 */
public class UcuSwap implements UcuCommand {
    @Override
    public String getCommandName() {
        return "swap";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var second = context.popValue();
        var first = context.popValue();
        context.pushValue(second);
        context.pushValue(first);
    }
}
