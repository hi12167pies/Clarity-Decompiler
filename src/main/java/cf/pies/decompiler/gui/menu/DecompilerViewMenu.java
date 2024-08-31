package cf.pies.decompiler.gui.menu;

import cf.pies.decompiler.Main;
import cf.pies.decompiler.decompile.CodeDecompiler;
import cf.pies.decompiler.gui.Menu;
import cf.pies.decompiler.gui.SwingGui;
import me.kuwg.clarity.ast.AST;

import javax.swing.*;

public class DecompilerViewMenu extends Menu {
    private final JTextArea textArea = new JTextArea();

    @Override
    public String getName() {
        return "Decompiled code";
    }

    @Override
    protected void createPanel(SwingGui gui) {
        textArea.setFont(gui.getMonospacedFont());
        update(gui);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);

        panel.add(scrollPane);
    }

    @Override
    public void update(SwingGui gui) {
        AST ast = gui.getData().ast;

        if (ast == null) {
            textArea.setEditable(false);
            textArea.setText("No file selected.");
        } else {
            textArea.setEditable(true);
            CodeDecompiler decompiler = new CodeDecompiler(gui.getData());
            decompiler.setNodeHandlers(Main.getDefaultNodeHandlers());
            decompiler.handleNode(ast.getRoot());
            textArea.setText(decompiler.getCode());
        }
    }
}
