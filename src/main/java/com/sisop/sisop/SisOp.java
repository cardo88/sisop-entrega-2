package com.sisop.sisop;

import com.sisop.sisop.UcuLang.UcuCommand;
import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.UcuLang;
import com.sisop.sisop.UcuLang.UcuValue;

import java.io.FileNotFoundException;
import java.io.IOException;

class Emoji implements UcuCommand {
    @Override
    public String getCommandName() {
        return "emoji";
    }

    @Override
    public void execute(UcuContext context) {
        context.pushValue(new UcuValue("🥸"));
        context.nextInstruction();
    }
}

class Sleep implements UcuCommand {
    @Override
    public String getCommandName() {
        return "sleep";
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        try {
            Thread.sleep((long)((double)value.value));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
                new Sleep(),
            }
        );

        while (uculang.next()) {}
    }
}
