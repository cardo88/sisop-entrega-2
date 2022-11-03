package com.sisop.sisop.OS.UI;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.sisop.sisop.OS.Process;
import com.sisop.sisop.OS.Scheduler;
import com.sisop.sisop.OS.Resources.Debugger;

import java.awt.BorderLayout;

public class DebuggerFrame extends JFrame {
    private final Process process;
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

    public DebuggerFrame(Process proc, Debugger dbg) {
        this.process = proc;
        this.debugger = dbg;

        setLayout(new BorderLayout());
        setSize(1000, 700);
        setTitle("-=- UcuLang Debugger (" + process.getName() + ") -=-");
        // setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                debugger.play(process.getPid());
            }
        });

        debugger.stop(process.getPid());

        instructionsTable = new JTable(
            process.getCode().getCompiledInstructions()
                .stream()
                .map(x -> new String[] { x.toString() })
                .collect(Collectors.toList())
                .toArray(new String[0][0]),
            new String[] { "Instrucciones" }
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
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                debugger.play(process.getPid());
            }
        });

        stepButton = new JButton("Step");
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                debugger.step(process.getPid());
                updateVariablesTable();
                updateStackTable();
                updateInstructionTable();
            }
        });

        stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                debugger.stop(process.getPid());
                updateVariablesTable();
                updateStackTable();
                updateInstructionTable();
            }
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
    }

    private void updateVariablesTable() {
        var model = new DefaultTableModel();
        model.setDataVector(
            process.getCode().getContext().getVariables().entrySet()
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
        var model = new DefaultTableModel();
        model.setDataVector(
            process.getCode().getContext().getStack()
                .stream()
                .map(x -> new String[] { x.toString() })
                .collect(Collectors.toList())
                .toArray(new String[0][0]), 
            new String[] { "Stack" }
        );
        stackTable.setModel(model);
    }

    private void updateLabelsTable() {
        var model = new DefaultTableModel();
        model.setDataVector(
            process.getCode().getContext().getLabels().entrySet()
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
        var pc = process.getCode().getContext().getProgramCounter();
        if (pc < instructionsTable.getModel().getRowCount()) {
            instructionsTable.setRowSelectionInterval(pc, pc);
            instructionsTable.scrollRectToVisible(new Rectangle(instructionsTable.getCellRect(pc, 0, true)));
        }
    }
}
