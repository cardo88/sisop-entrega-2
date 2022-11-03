package com.sisop.sisop.OS.UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.sisop.sisop.OS.ProcessFactory;
import com.sisop.sisop.OS.Scheduler;
import com.sisop.sisop.OS.Resources.Debugger;
import com.sisop.sisop.OS.Resources.SleepTimer;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;

public class OsFrame extends JFrame {
    private final ProcessFactory processFactory;
    private final Scheduler scheduler;
    private final Debugger debugger;
    private final SleepTimer sleepTimer;

//    private final JPanel mainPanel;
    private final JPanel programButtonsPanel;
    private final JPanel programsPanel;

    public OsFrame(ProcessFactory factory, Scheduler sch, Debugger dbg, SleepTimer sleepTimer, String[] programPaths, boolean maximize) {
        this.processFactory = factory;
        this.scheduler = sch;
        this.debugger = dbg;
        this.sleepTimer = sleepTimer;

        setLayout(new BorderLayout());
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("UcuNix");

        if (maximize) {
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            setMaximizedBounds(env.getMaximumWindowBounds());
            setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        }
//
//        mainPanel = new JPanel();
//        mainPanel.setLayout(new FlowLayout());
//        mainPanel.setBackground(Color.CYAN);

//        add(new JScrollPane(mainPanel), BorderLayout.CENTER);

        var unix = new JLabel(" -= UcuNix =-");
        unix.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));

        add(unix, BorderLayout.NORTH);

        programsPanel = new JPanel();
        programsPanel.setBackground(Color.CYAN);
        programsPanel.setLayout(new FlowLayout());

//        mainPanel.add(programsPanel);

        programButtonsPanel = new JPanel();
        programButtonsPanel.setLayout(new FlowLayout());

        var programsLabel = new JLabel(" -= Programas =-");
        programsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));

        programButtonsPanel.add(programsLabel);

        var processFactory = new ProcessFactory(sleepTimer, debugger);
        for (var path : programPaths) {
            var progButton = new JButton(path);
            programButtonsPanel.add(progButton);
            progButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    var programPanel = new ProgramPanel(scheduler, debugger);
                    try {
                        var process = processFactory.fromSourceFile(path, programPanel.getConsole());
                        scheduler.addProcess(process);
                        programPanel.setProcess(process);
                        programsPanel.add(programPanel);
                    } catch (IOException ex) {
                        Logger.getLogger(OsFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }

        add(new JScrollPane(programsPanel), BorderLayout.CENTER);
        add(programButtonsPanel, BorderLayout.SOUTH);

        doLoop();
    }

    private void doLoop() {
        Thread newThread = new Thread(() -> {
            while (true) {
                try {
                    SwingUtilities.invokeAndWait(() -> {
                        scheduler.step();
                        sleepTimer.step();
                        for (var component : programsPanel.getComponents()) {
                            if (component instanceof ProgramPanel p) {
                                p.update();
                            }
                        }
                    });
                } catch (InvocationTargetException | InterruptedException e) { }
            }
        });
        newThread.start();
    }
}
