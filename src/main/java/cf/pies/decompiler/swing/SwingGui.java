package cf.pies.decompiler.swing;

import cf.pies.decompiler.Code;
import cf.pies.decompiler.Main;
import me.kuwg.clarity.ast.AST;
import sun.swing.text.TextComponentPrintable;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.Printable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class SwingGui {
    public AST currentAst = null;
    public JTextArea textArea = new JTextArea();
    public JFrame frame = new JFrame("Clarity Decompiler");

    public final JButton astBtn = new JButton("AST");
    public final JButton codeBtn = new JButton("Code");
    public final JButton saveBtn = new JButton("Recompile edited code");
    public final JPanel buttonPanel = new JPanel();

    public boolean edited = false;
    public Menu menu = Menu.AST;

    public SwingGui() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea.setText("Drag and drop files here.");
        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!edited && menu == Menu.Code) {
                    buttonPanel.add(saveBtn);
                    buttonPanel.updateUI();
                    edited = true;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);

        // buttons
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));


        astBtn.addActionListener(e -> setMenu(Menu.AST));
        codeBtn.addActionListener(e -> setMenu(Menu.Code));
        saveBtn.addActionListener(new SaveButtonListener(this));

        buttonPanel.add(astBtn);
        buttonPanel.add(codeBtn);


        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        new DropTarget(textArea, new DropTargetImpl(this));
    }

    public void setMenu(Menu menu) {
        try {
            switch (menu) {
                case AST:
                    astMode();
                    break;
                case Code:
                    codeMode();
                    break;
            }
            this.menu = menu;
            buttonPanel.remove(saveBtn);
            buttonPanel.updateUI();
            edited = false;
        } catch (Exception e) {
            showError(e);
        }
    }

    private void astMode() {
        StringBuilder sb = new StringBuilder();
        currentAst.getRoot().print(sb, "");
        textArea.setText(sb.toString());
    }

    private void codeMode() {
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

    public void showError(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        exception.printStackTrace(writer);

        showDialog("Error", stringWriter.toString());
    }

    public void showDialog(String title, String text) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);

        JTextArea messageLabel = new JTextArea(text);

        JScrollPane scrollPane = new JScrollPane(messageLabel);
        scrollPane.setBorder(null);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());

        dialog.setLayout(new BorderLayout());
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(okButton, BorderLayout.SOUTH);

        dialog.setSize(400, 200);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(null);

        dialog.setVisible(true);
    }
}
