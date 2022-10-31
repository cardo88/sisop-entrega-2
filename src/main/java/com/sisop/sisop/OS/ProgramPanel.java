package com.sisop.sisop.OS;

import java.awt.*;
import java.awt.Color;

// import javax.swing.JScrollPane;
// import javax.swing.JTextArea;
import javax.swing.*;

/**
 *
 */
public class ProgramPanel extends JPanel {
    public final ConsolePanel consolePanel;
    private final JLabel title;
    private final JLabel state;
    private Process process = null;

    public ProgramPanel() {
        setLayout(new BorderLayout());

        title = new JLabel("");
        state = new JLabel();
        consolePanel = new ConsolePanel();

        var f = new JPanel();
        f.setLayout(new GridLayout(1, 2));
        f.add(title);
        var button = new JButton("Cerrar");
        f.add(button);


        // var button = new JButton("Cerrar");

        // add(title, BorderLayout.NORTH);
        // add(button, BorderLayout.SOUTH);
        add(f, BorderLayout.NORTH);
        add(state, BorderLayout.SOUTH);
        add(consolePanel, BorderLayout.CENTER);

        update();
    }

    public void setProcess(Process process) {
        this.process = process;
        title.setText(process.getName());
    }

    public void update() {
        if (process != null) {
            state.setText("PID: " + process.getPid().getId() + " | State: " + process.getState());
        } else {
            state.setText("PID: -1 | State: <none>");
        }
    }
}
