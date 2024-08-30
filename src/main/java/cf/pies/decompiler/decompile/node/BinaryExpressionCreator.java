package cf.pies.decompiler.decompile.node;

import cf.pies.decompiler.decompile.CodeDecompiler;
import cf.pies.decompiler.decompile.NodeHandler;
import com.google.common.collect.Sets;
import me.kuwg.clarity.ast.ASTNode;
import me.kuwg.clarity.ast.nodes.expression.BinaryExpressionNode;

import java.util.Set;

public class BinaryExpressionCreator implements NodeHandler {
    @Override
    public Set<Class<?>> getSupportedNodes() {
        return Sets.newHashSet(
                BinaryExpressionNode.class
        );
    }

    @Override
    public void handle(ASTNode _node, CodeDecompiler code) {
        BinaryExpressionNode node = (BinaryExpressionNode) _node;

        code.handleNode(node.getLeft());
        code.append(" ").append(node.getOperator()).append(" ");
        code.handleNode(node.getRight());
    }
}
