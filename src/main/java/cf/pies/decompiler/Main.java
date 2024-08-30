package cf.pies.decompiler;

import cf.pies.decompiler.decompile.NodeHandler;
import cf.pies.decompiler.decompile.node.*;
import cf.pies.decompiler.gui.SwingGui;
import cf.pies.decompiler.gui.menu.ASTViewMenu;
import cf.pies.decompiler.gui.menu.DecompilerViewMenu;
import cf.pies.decompiler.gui.menu.settings.SettingsMenu;
import cf.pies.decompiler.gui.menu.file.FileSelectionMenu;
import com.google.common.collect.Lists;

import java.util.List;

public class Main {
    private static List<NodeHandler> handlers = Lists.newArrayList(
            new BlockNodeCreator(),
            new FunctionNodeCreator(),
            new NativeNodeCreator(),
            new VariableNodeCreator(),
            new BinaryExpressionCreator(),
            new LiteralNodeCreator(),
            new ClassNodeCreator()
    );

    /**
     * @return A list of default node handlers for decompiling
     */
    public static List<NodeHandler> getDefaultNodeHandlers() {
        return handlers;
    }

    public static void main(String[] args) {
        SwingGui gui = new SwingGui();

        gui.addMenu(new FileSelectionMenu());
        gui.addMenu(new ASTViewMenu());
        gui.addMenu(new DecompilerViewMenu());
        gui.addMenu(new SettingsMenu());

        gui.open();
    }
}
