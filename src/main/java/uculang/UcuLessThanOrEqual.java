package uculang;

//import com.sisop.sisop.UcuLang.UcuCommand;
//import com.sisop.sisop.UcuLang.UcuContext;

/**
 *
 */
public class UcuLessThanOrEqual implements UcuCommand {
    @Override
    public String getCommandName() {
        return "<=";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        var second = context.popValue();
        var first = context.popValue();

        if (first instanceof Comparable x) {
            if (!(x.compareTo(second) <= 0)) {
                context.nextInstruction();
            }
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
