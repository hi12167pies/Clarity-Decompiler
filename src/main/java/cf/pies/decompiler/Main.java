package cf.pies.decompiler;

import cf.pies.decompiler.node.*;
import cf.pies.decompiler.swing.SwingGui;
import com.google.common.collect.Lists;

import java.util.List;

public class Main {
    public static List<NodeHandler> handlers = Lists.newArrayList(
            new BlockNodeCreator(),
            new FunctionNodeCreator(),
            new NativeNodeCreator(),
            new VariableNodeCreator(),
            new BinaryExpressionCreator(),
            new LiteralNodeCreator(),
            new ClassNodeCreator()
    );

    public static void main(String[] args) {
        new SwingGui().open();
    }
}
