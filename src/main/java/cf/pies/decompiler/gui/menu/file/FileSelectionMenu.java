package cf.pies.decompiler.gui.menu.file;

import cf.pies.decompiler.gui.Menu;
import cf.pies.decompiler.gui.SwingGui;
import me.kuwg.clarity.ast.AST;
import me.kuwg.clarity.compiler.ASTLoader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.io.File;

public class FileSelectionMenu extends Menu {
    @Override
    public String getName() {
        return "File selection";
    }

    @Override
    protected void createPanel(SwingGui gui) {
        panel.setLayout(new GridLayout());

        JButton button = new JButton("Click to select file (or drag into area)");

        new DropTarget(button, new FileSelectionDragListener(gui, this, button));

        button.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select File");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Compiled Clarity (*.cclr)", "cclr"));

            int selection = fileChooser.showOpenDialog(null);

            if (selection != JFileChooser.APPROVE_OPTION) return;

            setFile(gui, fileChooser.getSelectedFile());
        });
        panel.add(button);
    }

    public void setFile(SwingGui gui, File file) {
        try {
            ASTLoader loader = new ASTLoader(file);
            AST ast = loader.load();

            gui.getData().setFile(file);
            gui.getData().setAst(ast);

            // Open AST View
            gui.getTabs().setSelectedIndex(1);
        } catch (Exception e) {
            gui.showError(e);
        }
    }

    @Override
    public void update(SwingGui gui) {

    }
}
