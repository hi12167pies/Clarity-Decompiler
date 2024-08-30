package cf.pies.decompiler.decompile;

import me.kuwg.clarity.ast.ASTNode;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public class CodeDecompiler {
    private List<NodeHandler> nodeHandlers = new ArrayList<>();
    private int indentAmount = 0;
    private final StringBuilder builder = new StringBuilder();
    private final List<DecompileError> errors = new ArrayList<>();

    public void setNodeHandlers(List<NodeHandler> nodeHandlers) {
        this.nodeHandlers = nodeHandlers;
    }

    public List<DecompileError> getErrors() {
        return errors;
    }

    public StringBuilder getBuilder() {
        return builder;
    }

    public String getCode() {
        StringBuilder finalString = new StringBuilder();
        for (DecompileError error : errors) {
            if (error.getNode() != null) {
                finalString.append("// Error at line ")
                        .append(error.getNode().getLine())
                        .append(": ");
            } else {
                finalString.append("// Error: ");
            }
            finalString
                    .append(error)
                    .append("\n");
        }
        return builder.toString();
    }

    public CodeDecompiler indent() {
        indentAmount++;
        builder.append("\t");
        return this;
    }

    public CodeDecompiler removeIndent() {
        indentAmount--;
        int index = builder.length() - 1;
        if (builder.charAt(index) == '\t') {
            builder.deleteCharAt(index);
        }
        return this;
    }


    public CodeDecompiler append(String text) {
        builder.append(text);
        return this;
    }

    public CodeDecompiler appendParams(List<? extends ASTNode> params) {
        for (int i = 0; i < params.size(); i++) {
            handleNode(params.get(i));
            if (i != (params.size() - 1)) {
                append(", ");
            }
        }
        return this;
    }

    public CodeDecompiler newLine() {
        builder.append("\n");
        for (int i = 0; i < indentAmount; i++) {
            builder.append("\t");
        }
        return this;
    }

    public CodeDecompiler handleNode(ASTNode node) {
        for (NodeHandler nodeHandler : nodeHandlers) {
            for (Class<?> supportedNode : nodeHandler.getSupportedNodes()) {

                if (supportedNode.isInstance(node)) {
                    nodeHandler.handle(node, this);
                    return this;
                }

            }
        }
        errors.add(new DecompileError("Unknown node: " + node, node));
        return this;
    }
}
