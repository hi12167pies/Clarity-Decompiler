package cf.pies.decompiler.swing;

import cf.pies.decompiler.Code;
import cf.pies.decompiler.Main;
import me.kuwg.clarity.ast.AST;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;

public class SwingGui {
    public AST currentAst = null;
    public JTextArea textArea = new JTextArea();
    public JFrame frame = new JFrame("Clarity Decompiler");

    public SwingGui() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea.setText("Drag and drop files here.");

        JScrollPane scrollPane = new JScrollPane(textArea);

        // buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton ast = new JButton("AST");
        JButton code = new JButton("Code");

        ast.addActionListener(e -> astMode());
        code.addActionListener(e -> codeMode());

        buttonPanel.add(ast);
        buttonPanel.add(code);

        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        new DropTarget(textArea, new DropTargetImpl(this));
    }

    public void astMode() {
        StringBuilder sb = new StringBuilder();
        currentAst.getRoot().print(sb, "");
        textArea.setText(sb.toString());
    }

    public void codeMode() {
        Code code = new Code();
        code.nodeHandlers = Main.handlers;
        code.handleNode(currentAst.getRoot());
        textArea.setText(code.builder.toString());
    }

    public void open() {
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
