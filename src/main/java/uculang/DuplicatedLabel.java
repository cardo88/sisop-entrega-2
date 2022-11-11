package uculang;

/**
 *
 */
public class DuplicatedLabel extends CompilationError {
    public final String label;
    
    public DuplicatedLabel(String label) {
        this.label = label;
    }
    
    @Override
    public String toString() {
        return "Duplicated label: " + label;
    }
}
