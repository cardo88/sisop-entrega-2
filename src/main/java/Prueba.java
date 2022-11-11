/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import uculang.CompilationError;
import uculang.UcuContext;
import uculang.UcuInterpreter;
import uculang.UcuLang;
import uculang.UcuProgram;
import uculang.UnknownCommand;
import ucunix.Console;
import ucunix.RoundRobinScheduler;
import ucunix.Ucunix;

/**
 *
 * @author ucu
 */
public class Prueba {
    public static void main(String[] args) throws CompilationError, Exception {
        Ucunix ucunix = new Ucunix(new RoundRobinScheduler());
        
        var process = ucunix.createProcess("Pepe", "\"Hola\" console.println 1000 timer.sleep \"Chau\" console.println", null);
        process.setConsole(new Console() {
            @Override
            public void print(String output) {
                System.out.println(output);
            }

            @Override
            public void clear() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public String read() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
        
        while (true) {
            ucunix.update();
        }
        
//        UcuProgram program = UcuLang.compile("10 20 +");
//        UcuContext context = new UcuContext(program);
//        UcuInterpreter interpreter = new UcuInterpreter(context);
//        
//        while (!interpreter.isFinished()) {
//            interpreter.run();
//        }
    }
}
