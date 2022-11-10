import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import uculang.DuplicatedLabel;
import uculang.DuplicatedLocalLabel;
import uculang.LocalLabelWithoutParent;
import uculang.LocalVariableWithoutParent;
import uculang.UnknownCommand;
import ucunix.Console;
import ucunix.RoundRobinScheduler;
import ucunix.Scheduler;
import ucunix.Ucunix;

/**
 *
 */
public class Prueba {
    public static void main(String[] args) {
        Scheduler scheduler = new RoundRobinScheduler();
        Ucunix ucunix = new Ucunix(scheduler);

        try {
            var process = ucunix.createProcess("null", Files.readString(Path.of("programs/JuegoDeLaVida.uculang")));

            var con = new Console() {

                @Override
                public void print(String output) {
                    System.out.print(output);
                    
                }

                @Override
                public void clear() {
                }

                @Override
                public String read() {
                    return "";
                }
                
            };

            process.setConsole(con);

            ucunix.createProcess("null", Files.readString(Path.of("programs/Saludo.uculang")))
                  .setConsole(con);;

            while (true) {
                ucunix.update();
            }
        } catch (UnknownCommand | LocalLabelWithoutParent | LocalVariableWithoutParent | DuplicatedLabel
                | DuplicatedLocalLabel | IOException e) {
            throw new RuntimeException("AAA");
        }
    }
}
