package cf.pies.decompiler.gui.menu;

import cf.pies.decompiler.gui.Menu;
import cf.pies.decompiler.gui.SwingGui;
import me.kuwg.clarity.ast.AST;

import javax.swing.*;

public class ASTViewMenu extends Menu {
    private final JTextArea textArea = new JTextArea();

    @Override
    public String getName() {
        return "AST";
    }

    @Override
    protected void createPanel(SwingGui gui) {
        textArea.setEditable(false);
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
            textArea.setText("No file selected");
        } else {
            StringBuilder astTree = new StringBuilder();
            ast.getRoot().print(astTree, "");

            textArea.setText(astTree.toString());
        }
    }
}
