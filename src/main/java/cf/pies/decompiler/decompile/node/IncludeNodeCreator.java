package cf.pies.decompiler.decompile.node;

import cf.pies.decompiler.decompile.CodeDecompiler;
import cf.pies.decompiler.decompile.NodeHandler;
import com.google.common.collect.Sets;
import me.kuwg.clarity.ast.ASTNode;
import me.kuwg.clarity.ast.nodes.include.IncludeNode;

import java.util.Set;

public class IncludeNodeCreator implements NodeHandler {
    @Override
    public Set<Class<?>> getSupportedNodes() {
        return Sets.newHashSet(
                IncludeNode.class
        );
    }

    @Override
    public void handle(ASTNode _node, CodeDecompiler code) {
        if (_node instanceof IncludeNode) {
            IncludeNode node = (IncludeNode) _node;

            String name = (node.isNative() ? "(native) " : "") + node.getName();

            code.newLine()
                    .append("// Start of lib - ")
                    .append(name)
                    .newLine();

            code.handleNode(node.getIncluded());

            code.newLine()
                    .append("// End of lib - ")
                    .append(name)
                    .newLine();
        }
    }
}
