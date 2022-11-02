package com.sisop.sisop.OS;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        // setLayout(null);

        var panelito = new JPanel();
        panelito.setLayout(new BorderLayout());

        title = new JLabel("");
        state = new JLabel();
        consolePanel = new ConsolePanel();

        var f = new JPanel();
        f.setLayout(new GridLayout(1, 3, 4, 1));
        var button = new JButton("Cerrar");
        var button2 = new JButton("Código Fuente");
        // var button3 = new JButton("Suspender");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var frame = new SourceCodeFrame(process.getCode().getSourceCode());
                frame.setVisible(true);
            }
        });
        f.add(button2);
        // f.add(button3);
        f.add(button);

        var h = new JPanel();
        h.setLayout(new GridLayout(1, 2));
        h.add(state);
        h.add(title);


        // var button = new JButton("Cerrar");

        // add(title, BorderLayout.NORTH);
        // add(button, BorderLayout.SOUTH);
        panelito.add(f, BorderLayout.NORTH);
        panelito.add(h, BorderLayout.SOUTH);
        panelito.add(consolePanel, BorderLayout.CENTER);

        add(panelito);
        setBounds(0, 0, consolePanel.getWidth() + 5, consolePanel.getHeight() + 5);

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
