package com.sisop.sisop;

import com.sisop.sisop.OS.UI.UcunixDesktop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author ucu
 */
public class SisOp {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException 
                | InstantiationException 
                | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        var desktop = new UcunixDesktop();
        desktop.setVisible(true);
    }
}
