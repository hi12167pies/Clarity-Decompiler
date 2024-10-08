package cf.pies.decompiler;

import cf.pies.decompiler.decompile.NodeDecompileHandler;
import cf.pies.decompiler.decompile.node.*;
import cf.pies.decompiler.gui.SwingGui;
import cf.pies.decompiler.gui.menu.ASTViewMenu;
import cf.pies.decompiler.gui.menu.DecompilerViewMenu;
import cf.pies.decompiler.gui.menu.ObfuscateViewMenu;
import cf.pies.decompiler.gui.menu.settings.SettingsMenu;
import cf.pies.decompiler.gui.menu.file.FileSelectionMenu;
import com.google.common.collect.Lists;

import java.util.List;

public class Main {
    private static final List<NodeDecompileHandler> handlers = Lists.newArrayList(
            new BlockNodeCreator(),
            new FunctionNodeCreator(),
            new NativeNodeCreator(),
            new VariableNodeCreator(),
            new BinaryExpressionCreator(),
            new LiteralNodeCreator(),
            new ClassNodeCreator(),
            new IncludeNodeCreator(),
            new StatementNodeCreator()
    );

    /**
     * @return A list of default node handlers for decompiling
     */
    public static List<NodeDecompileHandler> getDefaultNodeHandlers() {
        return handlers;
    }

    public static void main(String[] args) {
        SwingGui gui = new SwingGui();

        gui.addMenu(new FileSelectionMenu());
        gui.addMenu(new ASTViewMenu());
        gui.addMenu(new DecompilerViewMenu());
        gui.addMenu(new ObfuscateViewMenu());
        gui.addMenu(new SettingsMenu());

        gui.open();
    }
}
