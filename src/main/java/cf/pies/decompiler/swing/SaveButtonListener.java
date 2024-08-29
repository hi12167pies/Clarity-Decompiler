package cf.pies.decompiler.swing;

import me.kuwg.clarity.compiler.ASTSaver;
import me.kuwg.clarity.parser.ASTParser;
import me.kuwg.clarity.token.Token;
import me.kuwg.clarity.token.Tokenizer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SaveButtonListener implements ActionListener {
    private final SwingGui gui;
    public SaveButtonListener(SwingGui gui) {
        this.gui = gui;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        List<Token> tokens = Tokenizer.tokenize(gui.textArea.getText());
        ASTParser parser = new ASTParser("none", "none", tokens);

        ASTSaver saver;
        try {
            saver = new ASTSaver(parser.parse());
        } catch (Exception ex) {
            gui.showError(ex);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("cclr", "cclr"));
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                saver.save(file);
            } catch (IOException ex) {
                gui.showError(ex);
            }
        } else {
        }
    }
}
