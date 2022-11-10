package uculang;

//import com.sisop.sisop.UcuLang.UcuCommand;
//import com.sisop.sisop.UcuLang.UcuContext;

/**
 *
 */
public class UcuDrop implements UcuCommand {
    @Override
    public String getCommandName() {
        return "drop";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        context.popValue();
    }
}
