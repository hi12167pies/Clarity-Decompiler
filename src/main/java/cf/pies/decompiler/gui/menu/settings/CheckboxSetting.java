package cf.pies.decompiler.gui.menu.settings;

import cf.pies.decompiler.gui.SwingGui;

import javax.swing.*;

public abstract class CheckboxSetting implements Setting {
    private final JCheckBox checkBox = new JCheckBox();
    protected SwingGui gui;

    public abstract void update(boolean checked);

    public void setChecked(boolean checked) {
        checkBox.setSelected(checked);
        update(checkBox.isSelected());
    }

    @Override
    public void addToGui(SwingGui gui, JPanel panel) {
        this.gui = gui;
        checkBox.addActionListener(event -> {
            update(checkBox.isSelected());
        });
        panel.add(checkBox);
    }
}
