/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uculang;

/**
 *
 * @author ucu
 */
public class UnknownCommand extends CompilationError {
    public final String commandName;
    
    public UnknownCommand(String commandName) {
        this.commandName = commandName;
    }
    
    @Override
    public String toString() {
        return "Unknown command: " + this.commandName;
    }
}
