package cf.pies.decompiler.decompile.node;

import cf.pies.decompiler.decompile.CodeDecompiler;
import cf.pies.decompiler.decompile.NodeHandler;
import com.google.common.collect.Sets;
import me.kuwg.clarity.ast.ASTNode;
import me.kuwg.clarity.ast.nodes.block.BlockNode;
import me.kuwg.clarity.ast.nodes.block.ReturnNode;
import me.kuwg.clarity.ast.nodes.literal.VoidNode;

import java.util.Set;

public class BlockNodeCreator implements NodeHandler {
    @Override
    public Set<Class<?>> getSupportedNodes() {
        return Sets.newHashSet(
                BlockNode.class,
                VoidNode.class,
                ReturnNode.class
        );
    }

    @Override
    public void handle(ASTNode _node, CodeDecompiler code) {
        if (_node instanceof BlockNode) {
            BlockNode node = (BlockNode) _node;
            for (ASTNode child : node.getChildrens()) {
                code.handleNode(child);
            }
        }

        if (_node instanceof VoidNode) {
            code.append("void");
        }

        if (_node instanceof ReturnNode) {
            ReturnNode node = (ReturnNode) _node;
            code.append("return ").handleNode(node.getValue()).newLine();
        }
    }
}
