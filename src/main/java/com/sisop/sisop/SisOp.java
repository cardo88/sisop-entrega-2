package com.sisop.sisop;

import com.sisop.sisop.OS.Scheduler;
import com.sisop.sisop.OS.SleepTimer;
import com.sisop.sisop.OS.ConsolePanel;
import com.sisop.sisop.OS.ProcessFactory;
import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.OS.ProgramPanel;

import java.awt.event.ActionListener;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;

import javax.swing.text.BadLocationException;

/**
 *
 * @author ucu
 */
public class SisOp {

    public static JFrame f;
    public static JTextArea area;
    public static Scheduler scheduler;
    public static SleepTimer sleepTimer;
    public static LinkedList<ProgramPanel> pp = new LinkedList<>();

    public static void main(String[] args) throws FileNotFoundException, IOException {
        scheduler = new Scheduler();
        sleepTimer = new SleepTimer(scheduler);
        // scheduler.setWaitTime(200);
        var processFactory = new ProcessFactory(sleepTimer);


        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        f = new JFrame();
        f.setLayout(new BorderLayout());
        f.setSize(1000, 500);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        f.setMaximizedBounds(env.getMaximumWindowBounds());
        f.setExtendedState(f.getExtendedState() | f.MAXIMIZED_BOTH);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // f.setContentPane(new JDesktopPane());

        var panel = new JPanel();
        // panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        // panel.setLayout(new GridLayout(3, 3));
        panel.setBackground(Color.CYAN);
        var sc = new JScrollPane(panel);
        // sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // f.add(panel, BorderLayout.CENTER);
        f.add(sc, BorderLayout.CENTER);
        f.setVisible(true);

        var unix = new JLabel(" -= UcuNix =-");
        unix.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        f.add(unix, BorderLayout.NORTH);

        var k = new JPanel();
        k.setLayout(new FlowLayout());

        var prgs = new JLabel(" -= Programas =-");
        prgs.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        k.add(prgs);

        File[] programs = new File("programs/").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".uculang");
            }
        });

        for (var prog : programs) {
            var b = new JButton();
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    var progp = new ProgramPanel();

                    pp.add(progp);

                    panel.add(progp);

                    com.sisop.sisop.OS.Process proc;
                    try {
                        proc = processFactory.fromSourceFile(prog.getPath(), progp.consolePanel);
                    progp.setProcess(proc);

                    scheduler.addProcess(proc);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        throw new RuntimeException("");
                    }
                }
            });
            b.setText(prog.getName());
            k.add(b);
        }

        f.add(k, BorderLayout.SOUTH);

        doLoop();
    }

    private static void doLoop() {
        Thread newThread = new Thread(() -> {
            while (true) {
                try {
                    SwingUtilities.invokeAndWait(() -> {
                        scheduler.step();
                        sleepTimer.step();
                        for (var x : pp) {
                            x.update();
                        }
                    });
                } catch (InvocationTargetException | InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }
        });
        newThread.start();
    }

}
