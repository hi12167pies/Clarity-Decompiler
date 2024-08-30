package cf.pies.decompiler.gui;

import cf.pies.decompiler.gui.menu.settings.DecompilerLineSetting;
import me.kuwg.clarity.ast.AST;

import java.io.File;

public class GuiData {
    private File file = null;
    private AST ast = null;

    // settings
    private DecompilerLineSetting.LineSetting lineSetting = DecompilerLineSetting.LineSetting.AST;

    public void setLineSetting(DecompilerLineSetting.LineSetting lineSetting) {
        this.lineSetting = lineSetting;
    }

    public DecompilerLineSetting.LineSetting getLineSetting() {
        return lineSetting;
    }

    public void setAst(AST ast) {
        this.ast = ast;
    }

    public AST getAst() {
        return ast;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
