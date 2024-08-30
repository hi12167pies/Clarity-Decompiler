package cf.pies.decompiler.gui.menu.settings;

import cf.pies.decompiler.gui.SwingGui;
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
    public void addToGui(SwingGui gui, JPanel panel) {
        // ast lines
        JLabel label = new JLabel("Decompiled new lines");
        panel.add(label);

        JComboBox<String> astLinesBox = new JComboBox<>();
        for (LineSetting value : LineSetting.values()) {
            astLinesBox.addItem(value.getDescription());
        }
        List<LineSetting> settings = Lists.newArrayList(LineSetting.values());
        int selected = settings.indexOf(gui.getData().getLineSetting());
        astLinesBox.setSelectedIndex(selected);

        astLinesBox.addActionListener(event -> {
            LineSetting setting = LineSetting.values()[astLinesBox.getSelectedIndex()];
            gui.getData().setLineSetting(setting);
        });

        panel.add(astLinesBox);
    }
}
