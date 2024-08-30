package cf.pies.decompiler.gui;

import cf.pies.decompiler.gui.menu.settings.impl.DecompilerLineSetting;
import me.kuwg.clarity.ast.AST;

import java.io.File;

public class GuiData {
    public File file = null;
    public AST ast = null;

    // settings
    public DecompilerLineSetting.LineSetting decompileLineSetting = DecompilerLineSetting.LineSetting.AST;
    public boolean decompileShowLines = false;
}
