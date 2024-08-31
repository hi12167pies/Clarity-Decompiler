package cf.pies.decompiler.gui.menu;

import cf.pies.decompiler.NodeError;
import cf.pies.decompiler.gui.Menu;
import cf.pies.decompiler.gui.SwingGui;
import cf.pies.decompiler.obfuscate.ASTObfuscator;
import me.kuwg.clarity.ast.AST;
import org.checkerframework.checker.units.qual.A;

import javax.swing.*;
import java.awt.*;

public class ObfuscateViewMenu extends Menu {
    @Override
    public String getName() {
        return "Obfuscate";
    }

    @Override
    protected void createPanel(SwingGui gui) {
        panel.setLayout(new FlowLayout());
        JButton button = new JButton("Click to obfuscate AST");
        button.addActionListener(event -> {
            AST ast = gui.getData().ast;
            if (ast == null) {
                gui.showDialog("Obfuscator", "No ast currently loaded.");
                return;
            }
            try {
                ASTObfuscator obfuscator = new ASTObfuscator(ast);
                obfuscator.obfuscate();

                if (obfuscator.getErrors().isEmpty()) {
                    gui.showDialog("Obfuscator", "AST obfuscated.");

                } else {
                    StringBuilder result = new StringBuilder();
                    result.append("AST obfuscated.\n")
                            .append(obfuscator.getErrors().size()).append(" errors occurred while obfuscating AST.\n\n");
                    for (NodeError error : obfuscator.getErrors()) {
                        result.append("Error: ").append(error.getDescription()).append("\n");
                    }

                    gui.showDialog("Obfuscator", result.toString());

                }

            } catch (Exception e) {
                gui.showError(e);
            }
        });
        panel.add(button);
    }

    @Override
    public void update(SwingGui gui) {
    }
}
