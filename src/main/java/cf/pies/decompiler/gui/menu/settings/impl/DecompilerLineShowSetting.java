package cf.pies.decompiler.gui.menu.settings.impl;

import cf.pies.decompiler.gui.menu.settings.CheckboxSetting;

public class DecompilerLineShowSetting extends CheckboxSetting {
    @Override
    public String getName() {
        return "Show lines in decompiled output";
    }

    @Override
    public void update(boolean checked) {
        gui.getData().decompileShowLines = checked;
    }
}
