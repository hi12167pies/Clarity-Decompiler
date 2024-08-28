package cf.pies.decomplie.node;

import cf.pies.decomplie.Code;
import cf.pies.decomplie.NodeHandler;
import com.google.common.collect.Sets;
import me.kuwg.clarity.ast.ASTNode;
import me.kuwg.clarity.ast.nodes.literal.VoidNode;
import me.kuwg.clarity.ast.nodes.variable.assign.VariableDeclarationNode;
import me.kuwg.clarity.ast.nodes.variable.assign.VariableReassignmentNode;
import me.kuwg.clarity.ast.nodes.variable.get.VariableReferenceNode;

import java.util.Set;

public class VariableNodeCreator implements NodeHandler {

    @Override
    public Set<Class<?>> getSupportedNodes() {
        return Sets.newHashSet(
                VariableDeclarationNode.class,
                VariableReferenceNode.class,
                VariableReassignmentNode.class
        );
    }

    @Override
    public void handle(ASTNode _node, Code code) {
        if (_node instanceof VariableDeclarationNode) {
            VariableDeclarationNode node = (VariableDeclarationNode) _node;
            code.append("var ")
                    .append(node.getName());

            if (node.getValue() instanceof VoidNode) {
                code.newLine();
            } else {
                code.append(" = ")
                        .handleNode(node.getValue());
            }
        }

        if (_node instanceof VariableReferenceNode) {
            VariableReferenceNode node = (VariableReferenceNode) _node;
            code.append(node.getName());
        }

        if (_node instanceof VariableReassignmentNode) {
            VariableReassignmentNode node = (VariableReassignmentNode) _node;

            code.append(node.getName() + " = ")
                    .handleNode(node.getValue())
                    .newLine();
        }
    }
}
