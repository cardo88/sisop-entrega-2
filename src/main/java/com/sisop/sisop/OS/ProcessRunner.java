package com.sisop.sisop.OS;

/** 
 * 
 */
public class ProcessRunner {
    public enum State {
        Running,
        Paused,
        Finished,
    }

    // Cantidad máxima de instrucciones a ejecutar antes de
    // ceder el CPU a otro proceso.
    public final int maxSteps;

    // Instancia del proceso a ejecutar
    public final Process process;

    // El estado actual del proceso (en ejecución, pausado, terminado)
    private State currentState;

    // La cantidad de instrucciones que van siendo ejecutadas desde
    // el último estado de "pausa" (o desde el comienzo)
    private int stepCount;

    /**
     * 
     * @param process
     * @param maxInstructions
     */
    public ProcessRunner(Process process, int maxInstructions) {
        assert process != null : "Process cannot be null";

        this.maxSteps = maxInstructions;
        this.process = process;
        this.currentState = State.Running;
        this.stepCount = 0;
    }

    /**
     * 
     * @return
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * 
     */
    public void step() {
        boolean stillGoing = process.step();
        if (stillGoing) {
            stepCount++;
            if (stepCount >= maxSteps) {
                stepCount = 0;
                currentState = State.Paused;
            } else {
                currentState = State.Running;
            }
        } else {
            stepCount = 0;
            currentState = State.Finished;
        }
    }
}
