package com.sisop.sisop.OS.UI;

import java.io.IOException;

import com.sisop.sisop.FileReader;
import com.sisop.sisop.OS.Debugger;
import com.sisop.sisop.OS.ProcessId;
import com.sisop.sisop.OS.Process;
import com.sisop.sisop.OS.RoundRobinScheduler;
import com.sisop.sisop.OS.Scheduler;
import com.sisop.sisop.OS.Sdk.UcunixSdk;
import com.sisop.sisop.OS.Sdk.Timer.UcunixTimer;
import com.sisop.sisop.UcuLang.UcuContext;
import com.sisop.sisop.UcuLang.UcuInterpreter;
import com.sisop.sisop.UcuLang.UcuLang;
import com.sisop.sisop.UcuLang.Exceptions.CompilationError;

import java.io.File;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

/**
 *
 */
public class UcunixDesktop extends javax.swing.JFrame {

    private final Scheduler scheduler;
    private final UcunixTimer timer;
    private final Debugger debugger;
    
    /**
     * Creates new form UcunixDesktop
     * 
     */
    public UcunixDesktop() {
        this.scheduler = new RoundRobinScheduler();
        this.timer = new UcunixTimer(scheduler);
        this.debugger = new Debugger(scheduler);

        initComponents();        
        doLoop();
        
        programFilesTree.loadProgramFilesTree(new File("programs"), (File path) -> {
            loadProgram(path.getPath());
        });
    }
    
    private void doLoop() {
        Thread newThread = new Thread(() -> {
            while (true) {
                try {
                    SwingUtilities.invokeAndWait(() -> {
                        scheduler.run();
                        timer.update();
                        processesPanel.updatePrograms();
                    });
                } catch (InvocationTargetException | InterruptedException e) { 
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }
        });
        newThread.start();
    }

    
    private void loadProgram(String programFile) {
        try {
            var pid = new ProcessId();

            var src = FileReader.readFile(programFile);
            
            var programWindow = new ProgramWindow(pid, scheduler, debugger);
            
            var ucuProgram = UcuLang.compile(src, UcunixSdk.getCommands(
                pid, scheduler, programWindow.getConsoleWidget(), debugger, timer
            ));

            var context = new UcuContext(ucuProgram);

            var process = new Process(programFile, pid, new UcuInterpreter(context));

            scheduler.add(process);
            
            processesPanel.addProgramWindow(programWindow);
        } catch (IOException e) { 
            // --
        } catch (CompilationError e) {
            var dialog = new CompilationErrorDialog(this, true, e);
            dialog.setVisible(true);
        }
    }
    
    private void onSemaphoresButtonClicked() {
        var frame = new SemaphoresViewerFrame();
        frame.setVisible(true);
    }
    
    // ==========================================================
    // ==========================================================

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainHorizontalSplit = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        programFilesTree = new com.sisop.sisop.OS.UI.ProgramFilesTree();
        jPanel1 = new javax.swing.JPanel();
        semaphoresButton = new javax.swing.JButton();
        processesPanel = new com.sisop.sisop.OS.UI.ProcessesPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ucunix");

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setTopComponent(programFilesTree);

        semaphoresButton.setText("Semáforos");
        semaphoresButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSemaphoresButtonClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(semaphoresButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(semaphoresButton)
                .addGap(243, 243, 243))
        );

        jSplitPane1.setRightComponent(jPanel1);

        mainHorizontalSplit.setLeftComponent(jSplitPane1);
        mainHorizontalSplit.setRightComponent(processesPanel);

        getContentPane().add(mainHorizontalSplit, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onSemaphoresButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSemaphoresButtonClicked
        onSemaphoresButtonClicked();
    }//GEN-LAST:event_onSemaphoresButtonClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane mainHorizontalSplit;
    private com.sisop.sisop.OS.UI.ProcessesPanel processesPanel;
    private com.sisop.sisop.OS.UI.ProgramFilesTree programFilesTree;
    private javax.swing.JButton semaphoresButton;
    // End of variables declaration//GEN-END:variables
}
