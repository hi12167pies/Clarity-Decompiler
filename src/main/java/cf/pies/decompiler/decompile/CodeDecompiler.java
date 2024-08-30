package cf.pies.decompiler.decompile;

import cf.pies.decompiler.gui.GuiData;
import cf.pies.decompiler.gui.menu.settings.DecompilerLineSetting;
import me.kuwg.clarity.ast.ASTNode;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public class CodeDecompiler {
    private final GuiData guiData;

    public CodeDecompiler(GuiData guiData) {
        this.guiData = guiData;
    }

    private List<NodeHandler> nodeHandlers = new ArrayList<>();
    private int indentAmount = 0;
    private final StringBuilder builder = new StringBuilder();
    private final List<DecompileError> errors = new ArrayList<>();

    public void setNodeHandlers(List<NodeHandler> nodeHandlers) {
        this.nodeHandlers = nodeHandlers;
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
                    .append(error.getDescription())
                    .append("\n");
        }
        finalString.append(builder);
        return finalString.toString();
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

    /**
     * This will assume a new line, this will be here for if the line numbers get obfuscated
     */
    public CodeDecompiler assumeNewLine() {
        if (guiData.getLineSetting() == DecompilerLineSetting.LineSetting.ASSUMED) {
            newLine();
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

    public int previousLine = 0;
    public CodeDecompiler handleNode(ASTNode node) {
        if (node == null) {
            errors.add(new DecompileError("Node is null"));
            return this;
        }
        if (previousLine != node.getLine() && guiData.getLineSetting() == DecompilerLineSetting.LineSetting.AST) {
            newLine();
        }
        previousLine = node.getLine();
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
