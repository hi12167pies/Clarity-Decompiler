package cf.pies.decompiler.decompile.node;

import cf.pies.decompiler.decompile.CodeDecompiler;
import cf.pies.decompiler.decompile.NodeDecompileHandler;
import com.google.common.collect.Sets;
import me.kuwg.clarity.ast.ASTNode;
import me.kuwg.clarity.ast.nodes.statements.IfNode;

import java.util.Set;

public class StatementNodeCreator implements NodeDecompileHandler {
    @Override
    public Set<Class<?>> getSupportedNodes() {
        return Sets.newHashSet(
                IfNode.class
        );
    }

    @Override
    public void handle(ASTNode _node, CodeDecompiler code) {
        if (_node instanceof IfNode) {
            IfNode node = (IfNode) _node;

            code.append("if ")
                    .handleNode(node.getCondition())
                    .openBlock();

            code.handleNode(node.getIfBlock());
            code.closeBlock();
        }
    }
}
