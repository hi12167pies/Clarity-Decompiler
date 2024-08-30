package cf.pies.decompiler.decompile.node;

import cf.pies.decompiler.decompile.CodeDecompiler;
import cf.pies.decompiler.decompile.NodeHandler;
import com.google.common.collect.Sets;
import me.kuwg.clarity.ast.ASTNode;
import me.kuwg.clarity.ast.nodes.clazz.ClassDeclarationNode;
import me.kuwg.clarity.ast.nodes.clazz.ClassInstantiationNode;
import me.kuwg.clarity.ast.nodes.clazz.NativeClassDeclarationNode;

import java.util.Set;

public class ClassNodeCreator implements NodeHandler {
    @Override
    public Set<Class<?>> getSupportedNodes() {
        return Sets.newHashSet(
                ClassDeclarationNode.class,
                ClassInstantiationNode.class,
                NativeClassDeclarationNode.class
        );
    }

    @Override
    public void handle(ASTNode _node, CodeDecompiler code) {
        if (_node instanceof ClassDeclarationNode) {
            ClassDeclarationNode node = (ClassDeclarationNode) _node;

            code.append("class ").append(node.getName())
                    .openBlock();

            if (node.getConstructor() != null) {
                code.handleNode(node.getConstructor());
            }

            code.handleNode(node.getBody());

            code.closeBlock();
        }

        if (_node instanceof NativeClassDeclarationNode) {
            NativeClassDeclarationNode node = (NativeClassDeclarationNode) _node;

            code.append("class native ").append(node.getName())
                    .openBlock();

            if (node.getConstructor() != null) {
                code.handleNode(node.getConstructor());
            }

            code.handleNode(node.getBody());

            code.closeBlock();
        }

        if (_node instanceof ClassInstantiationNode) {
            ClassInstantiationNode node = (ClassInstantiationNode) _node;

            code.append("new ")
                    .append(node.getName())
                    .append("(")
                    .appendParams(node.getParams())
                    .append(")")
                    .assumeNewLine();
        }
    }
}
