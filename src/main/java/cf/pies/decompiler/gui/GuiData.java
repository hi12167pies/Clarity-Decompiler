package cf.pies.decompiler.gui;

import me.kuwg.clarity.ast.AST;

import java.io.File;

public class GuiData {
    private File file = null;
    private AST ast = null;

    public void setFile(File file) {
        this.file = file;
    }

    public void setAst(AST ast) {
        this.ast = ast;
    }

    public File getFile() {
        return file;
    }

    public AST getAst() {
        return ast;
    }
}
