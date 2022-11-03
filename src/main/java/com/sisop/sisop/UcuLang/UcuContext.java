package com.sisop.sisop.UcuLang;

import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

/**
 * 
 */
public class UcuContext {
    private final Map<String, Integer> labels = new HashMap<>();
    private final Map<String, UcuValue> variables = new HashMap<>();
    private final Stack<UcuValue> stack = new Stack<>();
    private final Stack<Integer> callStack = new Stack<>();

    private int programCounter = 0;

    public UcuContext() {
        //
    }

    public void pushValue(UcuValue value) {
        stack.push(value);
    }

    public UcuValue popValue() {
        return stack.pop();
    }

    public UcuValue peekValue() {
        return stack.peek();
    }

    public void setVariable(String name, UcuValue value) {
        variables.put(name, value);
    }

    public UcuValue getVariable(String name) {
        return variables.get(name);
    }

    public Map<String, UcuValue> getVariables() {
        return variables;
    }

    public Map<String, Integer> getLabels() {
        return labels;
    }

    public Stack<UcuValue> getStack() {
        return stack;
    }

    public void setLabel(String label, Integer index) {
        labels.put(label, index);
    }

    public Integer getLabel(String label) {
        return labels.get(label);
    }

    public void jumpTo(String label) {
        var dir = labels.get(label);
        if (dir == null) {
            throw new RuntimeException("Unknown label: " + label);
        }
        programCounter = dir;
    }

    public void call(String label) {
        callStack.push(programCounter);
        var dir = labels.get(label);
        if (dir == null) {
            throw new RuntimeException("Unknown function: " + label);
        }
        programCounter = dir;
    }

    public void ret() {
        programCounter = callStack.pop();
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void nextInstruction() {
        programCounter += 1;
    }

    public void skipOneInstruction() {
        programCounter += 2;
    }

    public void end() {
        programCounter = Integer.MAX_VALUE;
    }
}
