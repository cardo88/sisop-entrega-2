package uculang;

//import com.sisop.sisop.UcuLang.UcuContext;
//import com.sisop.sisop.UcuLang.UcuInstruction;

/**
 * 
 */
public class UcuDefineVariable implements UcuInstruction {
    private final String variableName;

    public UcuDefineVariable(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return "DefineVariable: " + variableName;
    }

    @Override
    public void execute(UcuContext context) {
        context.setVariable(variableName, context.popValue());
    }
}
