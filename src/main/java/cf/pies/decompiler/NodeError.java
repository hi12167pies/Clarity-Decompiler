package cf.pies.decompiler;

import me.kuwg.clarity.ast.ASTNode;

import javax.annotation.Nullable;

public class NodeError {
    private final ASTNode node;
    private final String description;

    public NodeError(String description) {
        this(description, null);
    }

    public NodeError(String description, @Nullable ASTNode node) {
        this.description = description;
        this.node = node;
    }

    @Nullable
    public ASTNode getNode() {
        return node;
    }

    public String getDescription() {
        return description;
    }
}
