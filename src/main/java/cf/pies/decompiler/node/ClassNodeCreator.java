package cf.pies.decompiler.node;

import cf.pies.decompiler.Code;
import cf.pies.decompiler.NodeHandler;
import com.google.common.collect.Sets;
import me.kuwg.clarity.ast.ASTNode;
import me.kuwg.clarity.ast.nodes.clazz.ClassDeclarationNode;
import me.kuwg.clarity.ast.nodes.clazz.ClassInstantiationNode;

import java.util.Set;

public class ClassNodeCreator implements NodeHandler {
    @Override
    public Set<Class<?>> getSupportedNodes() {
        return Sets.newHashSet(
                ClassDeclarationNode.class,
                ClassInstantiationNode.class
        );
    }

    @Override
    public void handle(ASTNode _node, Code code) {
        if (_node instanceof ClassDeclarationNode) {
            ClassDeclarationNode node = (ClassDeclarationNode) _node;

            code.append("class ").append(node.getName())
                    .append(" {");

            code.newLine().indent();

            code.handleNode(node.getBody());

            code.removeIndent();
            code.append("}").newLine();
        }

        if (_node instanceof ClassInstantiationNode) {
            ClassInstantiationNode node = (ClassInstantiationNode) _node;

            code.append("new ")
                    .append(node.getName())
                    .append("(")
                    .appendParams(node.getParams())
                    .append(")")
                    .newLine();
        }
    }
}
