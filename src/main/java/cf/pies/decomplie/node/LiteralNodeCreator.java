package cf.pies.decomplie.node;

import cf.pies.decomplie.Code;
import cf.pies.decomplie.NodeHandler;
import com.google.common.collect.Sets;
import me.kuwg.clarity.ast.ASTNode;
import me.kuwg.clarity.ast.nodes.function.declare.ParameterNode;
import me.kuwg.clarity.ast.nodes.literal.IntegerNode;
import me.kuwg.clarity.ast.nodes.literal.LiteralNode;

import java.util.Set;

public class LiteralNodeCreator implements NodeHandler {
    @Override
    public Set<Class<?>> getSupportedNodes() {
        return Sets.newHashSet(
                LiteralNode.class,
                ParameterNode.class,
                IntegerNode.class
        );
    }

    @Override
    public void handle(ASTNode _node, Code code) {
        if (_node instanceof LiteralNode) {
            LiteralNode node = (LiteralNode) _node;
            code.append("\"").append(node.getValue()).append("\"");
        }

        if (_node instanceof ParameterNode) {
            ParameterNode node = (ParameterNode) _node;
            code.append(node.getName());
        }

        if (_node instanceof IntegerNode) {
            IntegerNode node = (IntegerNode) _node;
            code.append(String.valueOf(node.getValue()));
        }
    }
}
