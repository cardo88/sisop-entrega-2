package com.sisop.sisop.UcuLang.Exceptions;

/**
 *
 */
public class LocalLabelWithoutParent extends CompilationError {
    public final String label;
    
    public LocalLabelWithoutParent(String label) {
        this.label = label;
    }
    
    @Override
    public String toString() {
        return "Local label without parent: " + label;
    }
}
