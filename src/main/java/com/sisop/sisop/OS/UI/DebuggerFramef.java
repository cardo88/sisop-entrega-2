package com.sisop.sisop.OS.UI;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.UcuLang.UcuInterpreter.StepMode;
import com.sisop.sisop.OS.Debugger;

import java.awt.BorderLayout;

public class DebuggerFramef extends JFrame {
    private final ProcessId processId;
    private final Debugger debugger;

    private final JSplitPane mainSplitPane;
    private final JPanel actionsPanel;
    private final JPanel inspectionPanel;
    private final JTable instructionsTable;
    private final JTable variablesTable;
    private final JTable labelsTable;
    private final JTable stackTable;
    private final JButton playButton;
    private final JButton stepButton;
    private final JButton stopButton;

    public DebuggerFramef(ProcessId pid, Debugger dbg) {
        this.processId = pid;
        this.debugger = dbg;

        var process = dbg.getScheduler().get(pid);

        setLayout(new BorderLayout());
        setSize(1000, 700);
        setTitle("-=- UcuLang Debugger (" + process.getName() + ") -=-");

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                debugger.dettach(process.getPid());
            }
        });

        var compiledInstructions = process.getInterpreter().getContext().getProgram().getInstructions();
        instructionsTable = new JTable(
            IntStream.range(0, compiledInstructions.size())
                     .mapToObj(x -> new String[] { String.valueOf(x), compiledInstructions.get(x).toString() })
                     .collect(Collectors.toList())
                     .toArray(new String[0][0]),
            new String[] { "PC", "Instrucciones" }
        );

        variablesTable = new JTable();
        labelsTable = new JTable();
        stackTable = new JTable();

        inspectionPanel = new JPanel();
        inspectionPanel.setLayout(new BoxLayout(inspectionPanel, BoxLayout.Y_AXIS));
        inspectionPanel.add(new JScrollPane(stackTable));
        inspectionPanel.add(new JScrollPane(variablesTable));
        inspectionPanel.add(new JScrollPane(labelsTable));

        mainSplitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT, 
            new JScrollPane(instructionsTable), 
            inspectionPanel
        );

        add(mainSplitPane, BorderLayout.CENTER);

        playButton = new JButton("Play");
        playButton.addActionListener((ActionEvent e) -> {
            debugger.play(process.getPid());
        });

        stepButton = new JButton("Step");
        stepButton.addActionListener((ActionEvent e) -> {
            debugger.stepInto(process.getPid());
        });

        stopButton = new JButton("Stop");
        stopButton.addActionListener((ActionEvent e) -> {
            debugger.pause(process.getPid());
        });

        actionsPanel = new JPanel();
        actionsPanel.add(playButton);
        actionsPanel.add(stepButton);
        actionsPanel.add(stopButton);

        add(actionsPanel, BorderLayout.NORTH);

        updateInstructionTable();
        updateVariablesTable();
        updateStackTable();
        updateLabelsTable();

        debugger.attach(process.getPid());
        process.getInterpreter().addStepModeChangedAction((StepMode pre, StepMode post) -> {
            if (post == StepMode.Pause) {
                updateVariablesTable();
                updateStackTable();
                updateInstructionTable();
            }
        });
        debugger.pause(process.getPid());
    }

    private void updateVariablesTable() {
        var process = debugger.getScheduler().get(processId);
        var model = new DefaultTableModel();
        model.setDataVector(
            process.getInterpreter().getContext().getVariables().entrySet()
                .stream()
                .sorted((a, b) -> a.getKey().compareTo(b.getKey()))
                .map(x -> new String[] { x.getKey(), x.getValue().toString() })
                .collect(Collectors.toList())
                .toArray(new String[0][0]), 
            new String[] { "Variable", "Valor" }
        );
        variablesTable.setModel(model);
    }

    private void updateStackTable() {
        var process = debugger.getScheduler().get(processId);
        var model = new DefaultTableModel();
        model.setDataVector(
            process.getInterpreter().getContext().getStack()
                .stream()
                .map(x -> new String[] { x.toString() })
                .collect(Collectors.toList())
                .toArray(new String[0][0]), 
            new String[] { "Stack" }
        );
        stackTable.setModel(model);
    }

    private void updateLabelsTable() {
        var process = debugger.getScheduler().get(processId);
        var model = new DefaultTableModel();
        model.setDataVector(
            process.getInterpreter().getContext().getProgram().getLabels().entrySet()
                .stream()
                .sorted((a, b) -> a.getKey().compareTo(b.getKey()))
                .map(x -> new String[] { x.getKey(), x.getValue().toString() })
                .collect(Collectors.toList())
                .toArray(new String[0][0]), 
            new String[] { "Etiqueta", "PC" }
        );
        labelsTable.setModel(model);
    }

    private void updateInstructionTable() {
        var process = debugger.getScheduler().get(processId);
        var pc = process.getInterpreter().getContext().getProgramCounter();
        if (pc < instructionsTable.getModel().getRowCount()) {
            instructionsTable.setRowSelectionInterval(pc, pc);
            instructionsTable.scrollRectToVisible(new Rectangle(instructionsTable.getCellRect(pc, 0, true)));
        }
    }
}
