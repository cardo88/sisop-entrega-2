package uculang;

import java.util.List;
import java.util.Map;

/**
 * Programa compilado de UcuLang
 */
public class UcuProgram {

    private final String sourceCode;

    private final List<UcuInstruction> instructions;

    private final Map<String, Integer> labels;

    /**
     *
     * @param sourceCode
     * @param instructions
     * @param labels
     */
    public UcuProgram(String sourceCode, List<UcuInstruction> instructions, Map<String, Integer> labels) {
        this.sourceCode = sourceCode;
        this.instructions = instructions;
        this.labels = labels;
    }
    
    /**
     * 
     * @return 
     */
    public String getSourceCode() {
        return sourceCode;
    }

    /**
     *
     * @return
     */
    public List<UcuInstruction> getInstructions() {
        return instructions;
    }

    /**
     *
     * @return
     */
    public Map<String, Integer> getLabels() {
        return labels;
    }
}
