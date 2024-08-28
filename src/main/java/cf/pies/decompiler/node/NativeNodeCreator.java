package cf.pies.decompiler.node;

import cf.pies.decompiler.Code;
import cf.pies.decompiler.NodeHandler;
import com.google.common.collect.Sets;
import me.kuwg.clarity.ast.ASTNode;
import me.kuwg.clarity.ast.nodes.function.call.DefaultNativeFunctionCallNode;
import me.kuwg.clarity.ast.nodes.function.call.PackagedNativeFunctionCallNode;

import java.util.Set;

public class NativeNodeCreator implements NodeHandler {
    @Override
    public Set<Class<?>> getSupportedNodes() {
        return Sets.newHashSet(
                DefaultNativeFunctionCallNode.class,
                PackagedNativeFunctionCallNode.class
        );
    }

    @Override
    public void handle(ASTNode _node, Code code) {
        if (_node instanceof DefaultNativeFunctionCallNode) {
            DefaultNativeFunctionCallNode node = (DefaultNativeFunctionCallNode) _node;

            code.append("native.")
                    .append(node.getName())
                    .append("(")
                    .appendParams(node.getParams())
                    .append(")")
                    .newLine();
        }

        if (_node instanceof PackagedNativeFunctionCallNode) {
            PackagedNativeFunctionCallNode node = (PackagedNativeFunctionCallNode) _node;

            code.append("native.")
                    .append(node.getPackage())
                    .append(".")
                    .append(node.getName())
                    .append("(")
                    .appendParams(node.getParams())
                    .append(")")
                    .newLine();
        }
    }
}
