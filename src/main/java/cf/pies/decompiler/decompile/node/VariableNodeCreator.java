package cf.pies.decompiler.decompile.node;

import cf.pies.decompiler.decompile.CodeDecompiler;
import cf.pies.decompiler.decompile.NodeHandler;
import com.google.common.collect.Sets;
import me.kuwg.clarity.ast.ASTNode;
import me.kuwg.clarity.ast.nodes.literal.VoidNode;
import me.kuwg.clarity.ast.nodes.variable.assign.LocalVariableReassignmentNode;
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
                VariableReassignmentNode.class,
                LocalVariableReassignmentNode.class
        );
    }

    @Override
    public void handle(ASTNode _node, CodeDecompiler code) {
        if (_node instanceof VariableDeclarationNode) {
            VariableDeclarationNode node = (VariableDeclarationNode) _node;
            if (node.isStatic()) {
                code.append("static ");
            }
            if (node.isConstant()) {
                code.append("const ");
            }
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

            code.append(node.getName()).append(" = ")
                    .handleNode(node.getValue())
                    .newLine();
        }

        if (_node instanceof LocalVariableReassignmentNode) {
            LocalVariableReassignmentNode node = (LocalVariableReassignmentNode) _node;

            code.append("local.").append(node.getName()).append(" = ")
                    .handleNode(node.getValue())
                    .newLine();
        }
    }
}
