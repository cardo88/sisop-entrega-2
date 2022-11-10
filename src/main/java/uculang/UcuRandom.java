package uculang;

//import com.sisop.sisop.UcuLang.UcuCommand;
//import com.sisop.sisop.UcuLang.UcuContext;
//import com.sisop.sisop.UcuLang.Types.UcuNumber;

/**
 *
 */
public class UcuRandom implements UcuCommand {
    @Override
    public String getCommandName() {
        return "random";
    }

    @Override
    public String toString() {
        return getCommandName();
    }

    @Override
    public void execute(UcuContext context) {
        context.pushValue(new UcuNumber(Math.random()));
    }
}
