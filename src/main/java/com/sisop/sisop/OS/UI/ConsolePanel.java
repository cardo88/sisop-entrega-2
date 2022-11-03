package com.sisop.sisop.OS.UI;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.sisop.sisop.OS.Console;

/**
 * Panel con un TextArea que implementa la interfaz "Console"
 * Los programas de UcuLang pueden escribir en esta consola.
 */
public class ConsolePanel extends JPanel implements Console {
    public JTextArea textArea;
    public JScrollPane scrollPane;

    public ConsolePanel() {
        textArea = new JTextArea();
        textArea.setColumns(41);
        textArea.setRows(23);
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        new SmartScroller(scrollPane, SmartScroller.VERTICAL, SmartScroller.END);

        add(scrollPane);
    }

    @Override
    public void print(String output) {
        textArea.append(output);
    }

    @Override
    public void clear() {
        textArea.setText("");
    }
}
