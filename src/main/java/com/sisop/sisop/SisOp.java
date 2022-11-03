package com.sisop.sisop;

import com.sisop.sisop.OS.ProcessFactory;
import com.sisop.sisop.OS.Scheduler;
import com.sisop.sisop.OS.Resources.Debugger;
import com.sisop.sisop.OS.Resources.SleepTimer;
import com.sisop.sisop.OS.UI.OsFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author ucu
 */
public class SisOp {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        var scheduler = new Scheduler();
        var sleepTimer = new SleepTimer(scheduler);
        var debugger = new Debugger(scheduler);
        var processFactory = new ProcessFactory(sleepTimer, debugger);

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException 
                | InstantiationException 
                | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        File[] programs = new File("programs/").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".uculang");
            }
        });

        ArrayList<String> programPaths = new ArrayList<>();
        for (var program : programs) {
            programPaths.add(program.getPath());
        }

        var osFrame = new OsFrame(processFactory, scheduler, debugger, sleepTimer, programPaths.toArray(new String[0]), true);
        osFrame.setVisible(true);
    }
}
