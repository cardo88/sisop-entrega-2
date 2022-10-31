package com.sisop.sisop.OS;

import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.*;

public class SourceCodeFrame extends JFrame {
    private final JTextArea textArea;

    public SourceCodeFrame(String sourceCode) {
        setSize(1000, 700);
        setTitle("-=- UcuLang -=-");

        textArea = new JTextArea();
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        textArea.setText(sourceCode);
        textArea.setCaretPosition(0);
        textArea.setEditable(false);

        add(new JScrollPane(textArea));
    }
}
