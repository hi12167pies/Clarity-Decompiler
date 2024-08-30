package cf.pies.decompiler.gui.menu.settings;

import cf.pies.decompiler.gui.SwingGui;

import javax.swing.*;

public interface Setting {
    String getName();
    void addToGui(SwingGui gui, JPanel panel);
}
