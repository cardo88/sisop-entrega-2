package com.sisop.sisop.OS.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.sisop.sisop.OS.Console;
import com.sisop.sisop.OS.Process;
import com.sisop.sisop.OS.Scheduler;
import com.sisop.sisop.OS.Resources.Debugger;
import com.sisop.sisop.UcuLang.UcuLang;

/**
 *
 */
public class ProgramPanel extends JPanel {
    private final Scheduler scheduler;
    private final Debugger debugger;
    private Process process;

    private final ConsolePanel consolePanel;
    private final JLabel programNameLabel;
    private final JLabel programStateLabel;
    private final JPanel actionsPanel;
    private final JButton closeButton;
    private final JButton sourceCodeButton;
    private final JButton debuggerButton;
    private final JPanel statusLinePanel;

    public ProgramPanel(Scheduler sch, Debugger dbg) {
        this.scheduler = sch;
        this.debugger = dbg;
        this.process = null;

        setLayout(new BorderLayout());

        closeButton = new JButton("Cerrar");
        sourceCodeButton = new JButton("Código Fuente");
        debuggerButton = new JButton("Debugger");

        actionsPanel = new JPanel();
        actionsPanel.setLayout(new GridLayout(1, 3, 4, 1));
        actionsPanel.add(sourceCodeButton);
        actionsPanel.add(debuggerButton);
        actionsPanel.add(closeButton);

        programNameLabel = new JLabel();
        programStateLabel = new JLabel();

        statusLinePanel = new JPanel();
        statusLinePanel.setLayout(new GridLayout(1, 2));
        statusLinePanel.add(programStateLabel);
        statusLinePanel.add(programNameLabel);

        consolePanel = new ConsolePanel();

        add(actionsPanel, BorderLayout.NORTH);
        add(consolePanel, BorderLayout.CENTER);
        add(statusLinePanel, BorderLayout.SOUTH);

        setBounds(0, 0, consolePanel.getWidth() + 5, consolePanel.getHeight() + 5);

        update();
    }

    private void removeActionListeners(JButton button) {
        for(var actionListener : button.getActionListeners() ) {
            button.removeActionListener(actionListener);
        }
        assert button.getActionListeners().length == 0;
    }

    public void setProcess(Process proc) {
        this.process = proc;

        removeActionListeners(closeButton);
        closeButton.addActionListener((ActionEvent e) -> {
            scheduler.finalizeProcess(process.getPid());
            var parent = getParent();
            parent.remove((Component) ProgramPanel.this);
            parent.repaint();
        });

        removeActionListeners(sourceCodeButton);
        sourceCodeButton.addActionListener((ActionEvent e) -> {
            var frame = new SourceCodeFrame(process.getCode().getSourceCode());
            frame.setVisible(true);
        });

        removeActionListeners(debuggerButton);
        debuggerButton.addActionListener((ActionEvent e) -> {
            setStoppedColor();
            var frame = new DebuggerFrame(process, debugger);
            frame.setVisible(true);
        });
    }

    public Console getConsole() {
        return consolePanel;
    }

    public void update() {
        if (process != null) {
            programNameLabel.setText(process.getName());
            programStateLabel.setText("PID: " + process.getPid().getId() + " | State: " + process.getState());

            if (process.getCode().getStepMode() == UcuLang.StepMode.Stop) {
                setStoppedColor();
            } else {
                setPlayingColor();
            }
        } else {
            programStateLabel.setText("PID: -1 | State: <none>");
        }
    }
    
    private void setStoppedColor() {
        statusLinePanel.setBackground(Color.YELLOW);
    }
 
    private void setPlayingColor() {
        statusLinePanel.setBackground(UIManager.getColor ( "Panel.background" ));
    }
}
