package com.sisop.sisop.UcuLang;

import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

import com.sisop.sisop.UcuLang.Types.UcuType;

/**
 * Contexto de ejecución del intérprete de UcuLang
 */
public class UcuContext {

    // El programa en ejecución.
    private final UcuProgram program;

    // Mapa que asocia nombre de variables y su valor. 
    private final Map<String, UcuType> variables;

    // Pila de datos de UcuLang.
    private final Stack<UcuType> stack;

    // Pila de llamadas, aquí se guardan las direcciones (Program Counter)
    // de retorno luego de un Call.
    private final Stack<Integer> callStack;

    // Índice a la siguiente instrucción a ejecutar.
    private int programCounter;

    public UcuContext(UcuProgram program) {
        this.variables = new HashMap<>();
        this.stack = new Stack<>();
        this.callStack = new Stack<>();
        this.programCounter = 0;
        this.program = program;
    }

    /**
     * Devuelve el valor actual del program counter.
     *
     * @return El program counter.
     */
    public int getProgramCounter() {
        return programCounter;
    }

    /**
     * Empuja un valor a la pila de datos
     *
     * @param value El valor a insertar
     */
    public void pushValue(UcuType value) {
        stack.push(value);
    }

    /**
     * Extrae un valor de la pila de datos
     *
     * @return El valor extraído
     */
    public UcuType popValue() {
        return stack.pop();
    }

    /**
     * Devuelve el valor del tope de la pila (no lo extrae)
     *
     * @return El valor actualmente en el tope de la pila.
     */
    public UcuType peekValue() {
        return stack.peek();
    }

    /**
     * Agrega o actualiza una variable
     *
     * @param name El nombre de la variable
     * @param value El valor a insertar
     */
    public void setVariable(String name, UcuType value) {
        variables.put(name, value);
    }

    /**
     * Obtiene el valor actual de una variable
     *
     * @param name Nombre de la variable a consultar
     * @return El valor de la variable
     */
    public UcuType getVariable(String name) {
        return variables.get(name);
    }

    /**
     * Obtiene el mapa completo de las variables disponibles
     *
     * @return Mapa (Nombre, Valor) con todas las variables del sistema
     */
    public Map<String, UcuType> getVariables() {
        return variables;
    }

    /**
     *
     * @return
     */
    public UcuProgram getProgram() {
        return program;
    }

    /**
     * Devuelve la pila de datos del sistema
     *
     * @return Pila de valores
     */
    public Stack<UcuType> getStack() {
        return stack;
    }

    /**
     * Devuelve si aún hay instrucciones para ejecutar
     *
     * @return true si el programa está finalizado, falso de lo contrario.
     */
    public boolean isFinished() {
        return programCounter >= program.getInstructions().size();
    }

    /**
     * Marca el programa como finalizado
     *
     * Luego de ejecutar éste método, "isFinished()" devolverá true.
     */
    public void setFinished() {
        programCounter = Integer.MAX_VALUE;
    }

    /**
     * Devuelve la siguiente instrucción a ejecutar y avanza el program counter.
     *
     * @return La UcuInstruction a ser ejecutada.
     */
    public UcuInstruction nextInstruction() {
        if (!isFinished()) {
            return program.getInstructions().get(programCounter++);
        }
        return null;
    }

    /**
     *
     * @param label
     */
    public void jumpTo(String label) {
        var dir = program.getLabels().get(label);
        if (dir == null) {
            throw new RuntimeException("Unknown label: " + label);
        }
        programCounter = dir;
    }

    /**
     *
     * @param label
     * @return
     */
    public Integer call(String label) {
        callStack.push(programCounter);
        var dir = program.getLabels().get(label);
        if (dir == null) {
            throw new RuntimeException("Unknown function: " + label);
        }
        programCounter = dir;
        return programCounter;
    }

    /**
     *
     * @return
     */
    public Integer ret() {
        programCounter = callStack.pop();
        return programCounter;
    }
}
