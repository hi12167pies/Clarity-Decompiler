package cf.pies.decompiler.gui;

import javax.swing.*;

public abstract class Menu {
    protected JPanel panel = new JPanel();

    public abstract String getName();
    protected abstract void createPanel(SwingGui gui);
    public abstract void update(SwingGui gui);

    public JPanel getPanel(SwingGui gui) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        createPanel(gui);
        return this.panel;
    }
}
