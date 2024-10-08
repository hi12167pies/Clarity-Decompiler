package cf.pies.decompiler.decompile;

import cf.pies.decompiler.NodeError;
import cf.pies.decompiler.gui.GuiData;
import cf.pies.decompiler.gui.menu.settings.impl.DecompilerLineSetting;
import com.google.common.base.Strings;
import me.kuwg.clarity.ast.ASTNode;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public class CodeDecompiler {
    private final GuiData guiData;

    public CodeDecompiler(GuiData guiData) {
        this.guiData = guiData;
    }

    private List<NodeDecompileHandler> nodeHandlers = new ArrayList<>();
    private int indentAmount = 0;
    private final StringBuilder builder = new StringBuilder();
    private final List<NodeError> errors = new ArrayList<>();

    public void setNodeHandlers(List<NodeDecompileHandler> nodeHandlers) {
        this.nodeHandlers = nodeHandlers;
    }

    public String getCode() {
        StringBuilder finalString = new StringBuilder();
        for (NodeError error : errors) {
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
        if (guiData.decompileLineSetting == DecompilerLineSetting.LineSetting.ASSUMED) {
            newLine();
        }
        return this;
    }

    public CodeDecompiler newLine() {
        builder.append("\n");
        if (guiData.decompileShowLines) {
            builder.append("/# Line ")
                    .append(Strings.padStart(String.valueOf(previousLine), 3, ' '))
                    .append(" #/ ");
        }
        for (int i = 0; i < indentAmount; i++) {
            builder.append("\t");
        }
        return this;
    }

    public void openBlock() {
        append(" {").assumeNewLine().indent();
    }

    public void closeBlock() {
        removeIndent().
                newLine()
                .append("}")
                .assumeNewLine();
    }

    public int previousLine = 0;
    public CodeDecompiler handleNode(ASTNode node) {
        if (node == null) {
            errors.add(new NodeError("Node is null"));
            return this;
        }

        if (previousLine != node.getLine() && guiData.decompileLineSetting == DecompilerLineSetting.LineSetting.AST) {
            newLine();
        }

        previousLine = node.getLine();

        for (NodeDecompileHandler nodeHandler : nodeHandlers) {
            for (Class<?> supportedNode : nodeHandler.getSupportedNodes()) {

                if (supportedNode.isInstance(node)) {
                    nodeHandler.handle(node, this);
                    return this;
                }

            }
        }
        errors.add(new NodeError("Unknown node: " + node, node));
        return this;
    }
}
