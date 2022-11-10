/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uculang;

/**
 *
 * @author ucu
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
