package com.sisop.sisop;

import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.UcuLang;
import java.io.FileNotFoundException;
import java.io.IOException;

class Emoji implements UcuCommand {
    @Override
    public String getCommandName() {
        return "emoji";
    }

    @Override
    public void execute(UcuContext context) {
        System.out.println("🥸");
        context.nextInstruction();
    }
}

/**
 *
 * @author ucu
 */
public class SisOp {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        var uculang = new UcuLang();
        uculang.compile(
            FileReader.readFile("src/fuente.uculang"),
            new UcuCommand[] {
                new Emoji(),
            }
        );

        while (uculang.next()) {}
    }
}
