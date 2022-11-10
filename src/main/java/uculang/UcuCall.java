package uculang;

//import com.sisop.sisop.UcuLang.UcuContext;
//import com.sisop.sisop.UcuLang.UcuInstruction;

/**
 *
 */
public class UcuCall implements UcuInstruction {
    public final String label;

    public UcuCall(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Call: " + label;
    }

    @Override
    public void execute(UcuContext context) {
        context.call(label);
    }
}
