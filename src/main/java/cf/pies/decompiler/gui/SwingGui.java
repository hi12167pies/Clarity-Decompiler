package cf.pies.decompiler.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.ui.FlatTabbedPaneUI;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class SwingGui {
    private final GuiData guiData = new GuiData();
    private final List<Menu> menus = new ArrayList<>();

    // frame
    private final JFrame frame = new JFrame("Clarity Decompiler");

    // gui objects
    private final JPanel panel = new JPanel();
    private final JTabbedPane tabs = new JTabbedPane();

    // monospaced font
    private final Font monospacedFont = new Font("Consolas", Font.PLAIN, 12);

    public SwingGui() {
        FlatLaf.setup(new FlatDarkLaf());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // give tabs dark look
        tabs.setUI(new FlatTabbedPaneUI());
        tabs.addChangeListener(event -> {
            if (menus.size() <= tabs.getSelectedIndex()) return;
            Menu menu = menus.get(tabs.getSelectedIndex());
            menu.update(this);
        });

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(tabs);

        frame.setContentPane(panel);

        SwingUtilities.updateComponentTreeUI(frame);
    }

    public JPanel getPanel() {
        return panel;
    }

    public Font getMonospacedFont() {
        return monospacedFont;
    }

    public GuiData getData() {
        return guiData;
    }

    public JTabbedPane getTabs() {
        return tabs;
    }

    /**
     * Adds a menu to the gui
     */
    public void addMenu(Menu menu) {
        tabs.add(menu.getName(), menu.getPanel(this));
        menus.add(menu);
    }

    /**
     * Removes all menus
     */
    public void clearMenus() {
        tabs.removeAll();
        menus.clear();
    }

    /**
     * Opens the gui to user
     */
    public void open() {
        frame.setSize(750, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Shows a dialog with the stacktrace from an error
     */
    public void showError(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        exception.printStackTrace(writer);

        showDialog("Error", stringWriter.toString());
    }

    /**
     * Shows a dialog popup to the user
     */
    public void showDialog(String title, String text) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);

        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        textArea.setFont(getMonospacedFont());

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());

        dialog.setLayout(new BorderLayout());
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(okButton, BorderLayout.SOUTH);

        dialog.setSize(800, 500);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(null);

        dialog.setVisible(true);
    }
}
