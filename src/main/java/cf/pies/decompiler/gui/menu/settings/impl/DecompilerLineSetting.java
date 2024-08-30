package cf.pies.decompiler.gui.menu.settings.impl;

import cf.pies.decompiler.gui.SwingGui;
import cf.pies.decompiler.gui.menu.settings.Setting;
import com.google.common.collect.Lists;

import javax.swing.*;
import java.util.List;

public class DecompilerLineSetting implements Setting {
    public enum LineSetting {
        AST("AST Lines"),
        ASSUMED("Assumed lines");

        private final String description;

        LineSetting(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    @Override
    public String getName() {
        return "Decompiler new lines";
    }

    @Override
    public void addToGui(SwingGui gui, JPanel panel) {
        // ast lines

        JComboBox<String> astLinesBox = new JComboBox<>();
        for (LineSetting value : LineSetting.values()) {
            astLinesBox.addItem(value.getDescription());
        }
        List<LineSetting> settings = Lists.newArrayList(LineSetting.values());
        int selected = settings.indexOf(gui.getData().decompileLineSetting);
        astLinesBox.setSelectedIndex(selected);

        astLinesBox.addActionListener(event -> {
            gui.getData().decompileLineSetting = LineSetting.values()[astLinesBox.getSelectedIndex()];
        });

        panel.add(astLinesBox);
    }
}
