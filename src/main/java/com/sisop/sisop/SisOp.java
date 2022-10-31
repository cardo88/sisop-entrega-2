package com.sisop.sisop;

import com.sisop.sisop.OS.Scheduler;
import com.sisop.sisop.OS.SleepTimer;
import com.sisop.sisop.OS.ConsolePanel;
import com.sisop.sisop.OS.ProcessFactory;
import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.OS.ProgramPanel;

import java.awt.event.ActionListener;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.regex.Pattern;

import javax.swing.*;

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


        // try {
        //     UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        // } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        //         | UnsupportedLookAndFeelException e) {
        //     e.printStackTrace();
        // }

        f = new JFrame();
        f.setLayout(new BorderLayout());
        f.setSize(1000, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // f.setContentPane(new JDesktopPane());

        var panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        var prog1 = new ProgramPanel();
        var prog2 = new ProgramPanel();
        var prog3 = new ProgramPanel();
        var prog4 = new ProgramPanel();

        pp.add(prog1);
        pp.add(prog2);
        pp.add(prog3);
        pp.add(prog4);

        panel.add(prog1);
        panel.add(prog2);
        panel.add(prog3);
        panel.add(prog4);
        
        f.add(panel, BorderLayout.CENTER);
        f.setVisible(true);

        var proc1 = processFactory.fromSourceFile("programs/fuente.uculang", prog1.consolePanel);
        var proc2 = processFactory.fromSourceFile("programs/fuente2.uculang", prog2.consolePanel);
        var proc3 = processFactory.fromSourceFile("programs/fuente3.uculang", prog3.consolePanel);
        var proc4 = processFactory.fromSourceFile("programs/fuente2.uculang", prog4.consolePanel);

        prog1.setProcess(proc1);
        prog2.setProcess(proc2);
        prog3.setProcess(proc3);
        prog4.setProcess(proc4);

        scheduler.addProcess(proc1);
        scheduler.addProcess(proc2);
        scheduler.addProcess(proc3);
        scheduler.addProcess(proc4);

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
                }
            }
        });
        newThread.start();
    }

}
