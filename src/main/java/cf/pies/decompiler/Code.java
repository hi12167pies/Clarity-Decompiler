package cf.pies.decompiler;

import me.kuwg.clarity.ast.ASTNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Code {
    public List<NodeHandler> nodeHandlers = new ArrayList<>();
    public StringBuilder builder = new StringBuilder();
    public int indentAmount = 0;
    public List<String> errors = new ArrayList<>();

    public Code indent() {
        indentAmount++;
        builder.append("\t");
        return this;
    }

    public Code removeIndent() {
        indentAmount--;
        int index = builder.length() - 1;
        if (builder.charAt(index) == '\t') {
            builder.deleteCharAt(index);
        }
        return this;
    }


    public Code append(String text) {
        builder.append(text);
        return this;
    }

    public Code appendParams(List<? extends ASTNode> params) {
        for (int i = 0; i < params.size(); i++) {
            handleNode(params.get(i));
            if (i != (params.size() - 1)) {
                append(", ");
            }
        }
        return this;
    }

    public Code newLine() {
        builder.append("\n");
        for (int i = 0; i < indentAmount; i++) {
            builder.append("\t");
        }
        return this;
    }

    public Code handleNode(ASTNode node) {
        for (NodeHandler nodeHandler : nodeHandlers) {
            for (Class<?> supportedNode : nodeHandler.getSupportedNodes()) {

                if (supportedNode.isInstance(node)) {
                    nodeHandler.handle(node, this);
                    return this;
                }

            }
        }
        errors.add("Unknown node: " + node);
        return this;
    }
}
