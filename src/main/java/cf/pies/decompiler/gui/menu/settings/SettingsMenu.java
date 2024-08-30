package cf.pies.decompiler.gui.menu.settings;

import cf.pies.decompiler.gui.Menu;
import cf.pies.decompiler.gui.SwingGui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SettingsMenu extends Menu {
    private List<Setting> settings = new ArrayList<>();

    public SettingsMenu() {
        settings.add(new DecompilerLineSetting());
    }

    @Override
    public String getName() {
        return "Settings";
    }

    @Override
    protected void createPanel(SwingGui gui) {
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        for (Setting setting : settings) {
            setting.addToGui(gui, panel);
        }
    }

    @Override
    public void update(SwingGui gui) {

    }
}
