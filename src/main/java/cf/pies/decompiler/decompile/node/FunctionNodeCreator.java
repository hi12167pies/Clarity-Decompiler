package cf.pies.decompiler.decompile.node;

import cf.pies.decompiler.decompile.CodeDecompiler;
import cf.pies.decompiler.decompile.NodeHandler;
import com.google.common.collect.Sets;
import me.kuwg.clarity.ast.ASTNode;
import me.kuwg.clarity.ast.nodes.function.call.FunctionCallNode;
import me.kuwg.clarity.ast.nodes.function.call.LocalFunctionCallNode;
import me.kuwg.clarity.ast.nodes.function.call.ObjectFunctionCallNode;
import me.kuwg.clarity.ast.nodes.function.declare.FunctionDeclarationNode;
import me.kuwg.clarity.ast.nodes.function.declare.ParameterNode;

import java.util.List;
import java.util.Set;

public class FunctionNodeCreator implements NodeHandler {
    @Override
    public Set<Class<?>> getSupportedNodes() {
        return Sets.newHashSet(
                FunctionDeclarationNode.class,
                LocalFunctionCallNode.class,
                FunctionCallNode.class,
                ObjectFunctionCallNode.class
        );
    }

    @Override
    public void handle(ASTNode _node, CodeDecompiler code) {
        if (_node instanceof FunctionDeclarationNode) {
            FunctionDeclarationNode node = (FunctionDeclarationNode) _node;
            String name = node.getFunctionName();
            List<ParameterNode> parameters = node.getParameterNodes();

            if (!name.equals("constructor")) {
                code.append("fn ");
            }
            code
                    .append(name)
                    .append("(")
                    .appendParams(parameters)
                    .append(") {");

            code.assumeNewLine().indent();
            code.handleNode(node.getBlock());
            code.removeIndent();

            code.newLine().append("}").assumeNewLine();
        }

        if (_node instanceof LocalFunctionCallNode) {
            LocalFunctionCallNode node = (LocalFunctionCallNode) _node;

            code.append("local." + node.getName())
                    .append("(")
                    .appendParams(node.getParams())
                    .append(")");
        }

        if (_node instanceof FunctionCallNode) {
            FunctionCallNode node = (FunctionCallNode) _node;

            code.append(node.getName())
                    .append("(")
                    .appendParams(node.getParams())
                    .append(")")
                    .assumeNewLine();
        }

        if (_node instanceof ObjectFunctionCallNode) {
            ObjectFunctionCallNode node = (ObjectFunctionCallNode) _node;

            code.append(node.getCaller() + "." + node.getCalled())
                    .append("(")
                    .appendParams(node.getParams())
                    .append(")")
                    .assumeNewLine();
        }
    }
}
