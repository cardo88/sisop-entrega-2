package uculang;

import java.util.ArrayList;

/**
 * Intérprete de UcuLang
 */
public class UcuInterpreter {
    public enum StepMode {
        Play,
        Pause,
        StepInto,
        StepOver,
    }

    public interface StepModeChangedAction {
        void onChange(StepMode previous, StepMode current);
    }

    // El contexto de ejecución del intérprete
    private final UcuContext context;

    // El modo de ejecución del programa.
    private StepMode stepMode;

    // 
    private final ArrayList<StepModeChangedAction> stepModeChangedActions;

    // 
    private int stepOverReturnAddress;

    /**
     * 
     * @param context
     */
    public UcuInterpreter(UcuContext context) {
        this.context = context;
        this.stepMode = StepMode.Play;
        this.stepOverReturnAddress = -1;
        this.stepModeChangedActions = new ArrayList<>();
    }

    /**
     * 
     * @param mode
     */
    public void setStepMode(StepMode mode) {
        StepMode previous = stepMode;

        stepMode = mode;

        for (var action : stepModeChangedActions) {
            action.onChange(previous, mode);
        }
    }
    
    /**
     * 
     * @return 
     */
    public UcuContext getContext() {
        return context;
    }

    /**
     * 
     * @param action
     */
    public void addStepModeChangedAction(StepModeChangedAction action) {
        stepModeChangedActions.add(action);
    }

    /**
     * 
     */
    public void clearStepModeChangedActions() {
        stepModeChangedActions.clear();
    }

    public boolean isFinished() {
        return context.isFinished();
    }

    /**
     * 
     * @return 
     */
    public boolean run() throws Exception {
        if (context.isFinished()) {
            return false;
        }

        switch (stepMode) {
            case Pause -> { 
                /* No ejecutar nada */ 
            }
            case Play -> {
                context.nextInstruction().execute(context);
            }
            case StepInto -> {
                context.nextInstruction().execute(context);
                setStepMode(StepMode.Pause);
            }
            case StepOver -> {
                var instruction = context.nextInstruction();

                if (stepOverReturnAddress == -1) {
                    // Se guarda la posición de retorno. Se ejecutarán
                    // instrucciones hasta que el program counter vuelva 
                    // a esta posición.
                    stepOverReturnAddress = context.getProgramCounter();
                } 

                instruction.execute(context);

                if (stepOverReturnAddress == context.getProgramCounter()) {
                    // Si el program counter volvió a la posición de 
                    // retorno, entonces se pausa la ejecución del programa.
                    setStepMode(StepMode.Pause);
                    stepOverReturnAddress = -1;
                }
            }
        }

        return !context.isFinished();
    }
}
