package com.sisop.sisop.UcuLang;

/**
 *
 */
public class UcuSet implements UcuCommand {
    @Override
    public String toString() {
        return "UcuSet(set)";
    }

    @Override
    public String getCommandName() {
        return "set";
    }

    @Override
    public void execute(UcuContext context) {
        var value = context.popValue();
        var index = context.popValue();
        var list = context.popValue();
        // System.out.println("-----------------------");
        // System.out.println("Set [ value ]: '" + value.toString() + "'");
        // System.out.println("Set [ index ]: " + index.toString());
        // System.out.println("Set [ list  ]: '" + list.toString() + "'");
        list.set(((Double)index.value).intValue(), value);
        context.nextInstruction();
    }
}
