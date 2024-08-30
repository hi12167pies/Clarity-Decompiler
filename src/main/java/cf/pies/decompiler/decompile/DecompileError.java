package cf.pies.decompiler.decompile;

import me.kuwg.clarity.ast.ASTNode;

import javax.annotation.Nullable;

public class DecompileError {
    private final ASTNode node;
    private final String description;

    public DecompileError(String description) {
        this(description, null);
    }

    public DecompileError(String description, @Nullable ASTNode node) {
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
