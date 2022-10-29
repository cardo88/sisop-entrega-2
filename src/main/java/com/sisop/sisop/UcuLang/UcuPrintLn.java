package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuPrintLn implements UcuInstruction {
    @Override
    public String toString() {
        return "UcuPrintLn(println)";
    }

    @Override
    public void execute(UcuContext context) {
        System.out.println(context.popValue().value.toString());
        context.nextInstruction();
    }
}
