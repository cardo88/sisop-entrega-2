package com.sisop.sisop.UcuLang.Exceptions;

/**
 *
 */
public class DuplicatedLocalLabel extends CompilationError {
    public final String parentLabel;
    public final String label;
    
    public DuplicatedLocalLabel(String parentLabel, String label) {
        this.parentLabel = parentLabel;
        this.label = label;
    }
    
    @Override
    public String toString() {
        return "Duplicated local label: " + label + " under parent label: " + parentLabel;
    }
}
