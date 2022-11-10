package uculang;

/**
 *
 */
public class LocalVariableWithoutParent extends CompilationError {
    public final String variable;
    
    public LocalVariableWithoutParent(String variable) {
        this.variable = variable;
    }
    
    @Override
    public String toString() {
        return "Local variable without parent: " + variable;
    }
}
