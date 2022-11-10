package uculang;

public class InvalidTypesRuntimeException extends RuntimeException {
    public final String[][] expectedTypeNames;
    public final String[][] foundTypeNames;
    public final String operation;
    
    public InvalidTypesRuntimeException(String operation, String[][] expected, String[][] found) {
        this.operation = operation;
        this.expectedTypeNames = expected;
        this.foundTypeNames = found;
    }
}
