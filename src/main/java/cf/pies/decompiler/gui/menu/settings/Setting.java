package cf.pies.decompiler.gui.menu.settings;

import cf.pies.decompiler.gui.SwingGui;

import javax.swing.*;

public interface Setting {
    void addToGui(SwingGui gui, JPanel panel);
}
