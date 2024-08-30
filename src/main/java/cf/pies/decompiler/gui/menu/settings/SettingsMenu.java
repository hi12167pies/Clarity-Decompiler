package cf.pies.decompiler.gui.menu.settings;

import cf.pies.decompiler.gui.Menu;
import cf.pies.decompiler.gui.SwingGui;
import cf.pies.decompiler.gui.menu.settings.impl.DecompilerLineSetting;
import cf.pies.decompiler.gui.menu.settings.impl.DecompilerLineShowSetting;
import com.google.common.collect.Lists;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SettingsMenu extends Menu {
    private final List<Setting> settings;

    public SettingsMenu() {
        settings = Lists.newArrayList(
                new DecompilerLineSetting(),
                new DecompilerLineShowSetting()
        );
    }

    @Override
    public String getName() {
        return "Settings";
    }

    @Override
    protected void createPanel(SwingGui gui) {
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        for (Setting setting : settings) {
            JPanel newPanel = new JPanel();
            newPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel label = new JLabel(setting.getName());
            panel.add(label);

            setting.addToGui(gui, panel);

            panel.add(newPanel);
        }

    }

    @Override
    public void update(SwingGui gui) {

    }
}
